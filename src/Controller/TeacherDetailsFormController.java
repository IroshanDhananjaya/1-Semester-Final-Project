package Controller;

import Controller.EntityController.ClassAndSectionController;
import Controller.EntityController.RoomAndHostalController;
import Controller.EntityController.SubjectController;
import Controller.EntityController.TeacherController;
import DB.DbConnection;
import Model.StudentandSport;
import Model.Teacher;
import Util.AlertMassage;
import View.TM.*;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TeacherDetailsFormController {
    public AnchorPane teacherDetailsContext;
    public Label lblTeacherName;
    public Label lblGender;
    public Label lblReligion;
    public Label lblAddress;
    public Label lblContact;
    public ComboBox cmbTeacherID;
    public TableView <TeachersClass >tblClasses;
    public TableColumn colClassID;
    public TableColumn colName;
    public TableView<TeacherSubjectTM>tblTeacherSub;
    public TableColumn colSubjectID;
    public TableColumn colSubjectName;
    public JFXComboBox cmbClassID;
    public AnchorPane addclassContext;
    public Label lblTphoto;
    public ImageView imgWomen;
    public ImageView imgMen;
    public Label lblClassName;

    public void initialize(){
        imgMen.setVisible(false);
        imgWomen.setVisible(false);

        colClassID.setCellValueFactory(new PropertyValueFactory<>("classID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("className"));

        colSubjectID.setCellValueFactory(new PropertyValueFactory<>("subjectID"));
        colSubjectName.setCellValueFactory(new PropertyValueFactory<>("subjectName"));

        try {
            setTeachersID();
            loadClassID();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        cmbTeacherID.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
                    try {
                        lblTphoto.setVisible(false);
                        addclassContext.setVisible(true);
                       setTeacherData(new TeacherController().getTeacher((String)newValue));
                       loadClasses(new TeacherController().getTeacherClasses((String)newValue));
                       loadSubjects(new TeacherController().getTeacherSubject((String)newValue));
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                });
        cmbClassID.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
                    try {
                      setClassName((String)newValue);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                });
    }

    public void backOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../View/TeacherManageForm.fxml");
        Parent load = FXMLLoader.load(resource);
        teacherDetailsContext.getChildren().clear();
        teacherDetailsContext.getChildren().add(load);
    }

    public void setTeacherData(Teacher teacher){
        lblTeacherName.setText(teacher.getTeacherName());
        lblAddress.setText(teacher.getAddress());
        lblContact.setText(teacher.getContact());
        lblGender.setText(teacher.getGender());
        lblReligion.setText(teacher.getReligion());

        if(lblGender.getText().equals("Male")){
            imgMen.setVisible(true);
            imgWomen.setVisible(false);
        }else{
            imgMen.setVisible(false);
            imgWomen.setVisible(true);
        }
    }

    public void setTeachersID() throws SQLException, ClassNotFoundException {
        List<String> teachersIDs=new TeacherController().getTeacherIDs();
        cmbTeacherID.getItems().addAll(teachersIDs);
    }

    public void loadClasses(ArrayList<TeachersClass> teachersClasses){
        ObservableList<TeachersClass> observableList= FXCollections.observableArrayList();
        teachersClasses.forEach(e->{observableList.add(new TeachersClass(e.getClassID(),e.getClassName()) );});

        tblClasses.setItems(observableList);
    }

    public void loadSubjects(ArrayList<TeacherSubjectTM> subjectTMS){
        ObservableList<TeacherSubjectTM> observableList= FXCollections.observableArrayList();
        subjectTMS.forEach(e->{observableList.add(new TeacherSubjectTM(e.getSubjectID(),e.getSubjectName()) );});

        tblTeacherSub.setItems(observableList);
    }

    public void teacherEditOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../View/TeacherEditForm.fxml");
        Parent load = FXMLLoader.load(resource);
        teacherDetailsContext.getChildren().clear();
        teacherDetailsContext.getChildren().add(load);
    }

    public void deleteTeacherOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if(new TeacherController().deleteTeacher((String) cmbTeacherID.getValue())) {
           new AlertMassage().successMassage("Delete Successful");
        } else {
          new AlertMassage().errorMassage("Try Again");
        }
    }

    public void removeSubject(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if(tblTeacherSub.getSelectionModel().getSelectedItem()!=null){
            TeacherSubjectTM teacherSubjectTM=tblTeacherSub.getSelectionModel().getSelectedItem();
            if(new SubjectController().deleteSubjectFromTeacher((String) cmbTeacherID.getValue(),teacherSubjectTM.getSubjectID())){
                loadSubjects(new TeacherController().getTeacherSubject(((String)cmbTeacherID.getValue())));
                loadClasses(new TeacherController().getTeacherClasses((String)cmbTeacherID.getValue()));
               new AlertMassage().successMassage("Delete Successful");
            } else {
              new AlertMassage().errorMassage("Try Again");

            }
        }else{
           new AlertMassage().errorMassage("Please Select the row where the wants to remove");
        }
    }

    public void removeClass(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if(tblClasses.getSelectionModel().getSelectedItem()!=null){
            TeachersClass teachersClass=tblClasses.getSelectionModel().getSelectedItem();
            if(new ClassAndSectionController().deleteClassFromTeacher((String) cmbTeacherID.getValue(),teachersClass.getClassID())){
                loadClasses(new TeacherController().getTeacherClasses((String)cmbTeacherID.getValue()));
                new AlertMassage().successMassage("Delete Successful");
            } else {
                new AlertMassage().errorMassage("Try Again");

            }
        }else{
            new AlertMassage().errorMassage("Please Select the row where the wants to remove");
        }
    }
    public void loadClassID() throws SQLException, ClassNotFoundException {
        List<String> classIDss=new ClassAndSectionController().getClassIDs();
        cmbClassID.getItems().addAll(classIDss);

    }

    public void addClass(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        PreparedStatement stm;
        PreparedStatement stm1=DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `Teacher " +
                "and Class`" +
                " WHERE TeacherID=? AND ClassID=?");

        stm1.setString(1,(String) cmbTeacherID.getValue());
        stm1.setString(2,(String) cmbClassID.getValue());
        ResultSet rst=stm1.executeQuery();
        if(rst.next()){
            new AlertMassage().errorMassage("This Teacher already added in this class");
            return;
        }

        stm= DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO `Teacher and " +
                "Class` Values (?,?)");
        stm.setObject(1,cmbTeacherID.getValue());
        stm.setObject(2,cmbClassID.getValue());

        if(stm.executeUpdate()>0){
            loadClasses(new TeacherController().getTeacherClasses(((String)cmbTeacherID.getValue())));
            new AlertMassage().successMassage("Successful Added");
        } else {
            new AlertMassage().errorMassage("Try Again");
        }

    }

    public void printTeacherDetailsOnAction(ActionEvent actionEvent) {
        try {
            /*Create a report using table data*/
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/View/report/TeacherDetails" +
                    ".jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(design);
            /*Get all values from table*/
            ObservableList<TeacherSubjectTM> items = tblTeacherSub.getItems();
            HashMap map=new HashMap();
            map.put("name",(String)lblTeacherName.getText());
            map.put("gender",(String)lblGender.getText());
            map.put("religion",(String)lblReligion.getText());
            map.put("address",(String)lblAddress.getText());
            map.put("contact",(String)lblContact.getText());


            /*Create a Bean Array Data Source and pass the table values to it*/
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, map,new JRBeanArrayDataSource(items.toArray()));
            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    public void setClassName(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm=DbConnection.getInstance().getConnection().prepareStatement("Select * From Class WHERE " +
                "ClassID='"+id+"'");
        ResultSet rst=stm.executeQuery();
        if(rst.next()){
            lblClassName.setText(rst.getString(2));
        }
    }
}
