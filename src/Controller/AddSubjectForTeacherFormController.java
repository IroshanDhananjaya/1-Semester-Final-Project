package Controller;

import Controller.EntityController.SubjectController;
import Controller.EntityController.TeacherController;
import DB.DbConnection;
import Model.Students;
import Model.SubjectandTeacher;
import Util.AlertMassage;
import View.TM.AllTeachersSubjectTM;
import View.TM.StudentTM;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddSubjectForTeacherFormController {
    public JFXComboBox cmbTeachers;
    public JFXComboBox cmbSubjects;
    public AnchorPane addSubjectToTeacherContext;
    public Label lblTeacher;
    public Label lblSubject;
    public TableView tblAllTeacherSubject;
    public TableColumn colTeacherID;
    public TableColumn colTeachersName;
    public TableColumn colSubjectID;
    public TableColumn colSubjectName;
    public TableColumn colSubjectMedium;

    public void initialize(){
        colTeacherID.setCellValueFactory(new PropertyValueFactory<>("teacherID"));
        colTeachersName.setCellValueFactory(new PropertyValueFactory<>("teacherName"));
        colSubjectID.setCellValueFactory(new PropertyValueFactory<>("sunjectID"));
        colSubjectName.setCellValueFactory(new PropertyValueFactory<>("subjectName"));
        colSubjectMedium.setCellValueFactory(new PropertyValueFactory<>("subjectMedium"));

        try {
            loadAllTeachernSubject(new SubjectController().getAllTeachersandSubject());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            loadSubjectName();
            loadTeacherName();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        cmbTeachers.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
                    try {
                       lblTeacher.setText(getTeacherName((String)newValue));
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                });
        cmbSubjects.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
                    try {
                        lblSubject.setText(getSubjectName((String)newValue));
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                });

    }

    public void addSubjectToTeacherOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        SubjectandTeacher subjectandTeacher=new SubjectandTeacher((String) cmbTeachers.getValue(),
                (String) cmbSubjects.getValue());

        if(new SubjectController().addSubjectToTeacher(subjectandTeacher)){
            loadAllTeachernSubject(new SubjectController().getAllTeachersandSubject());
           new AlertMassage().successMassage("Successful Added");
        }else{
          new AlertMassage().errorMassage("Already Added");
        }
    }

    public void loadTeacherName() throws SQLException, ClassNotFoundException {
        List<String>teacherNames=new TeacherController().getTeacherIDs();
        cmbTeachers.getItems().addAll(teacherNames);
    }
    public void loadSubjectName() throws SQLException, ClassNotFoundException {
        List<String>SubjectNames=new SubjectController().getSubjectId();
        cmbSubjects.getItems().addAll(SubjectNames);
    }

    public String getTeacherName(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm= DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM  Teachers " +
                "WHERE TeacherID=?");
        stm.setObject(1,id);
        ResultSet rst=stm.executeQuery();
        if(rst.next()){
            return rst.getString(2);
        }
        return null;
    }
    public String getSubjectName(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm= DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM  Subject " +
                "WHERE SubjectID=?");
        stm.setObject(1,id);
        ResultSet rst=stm.executeQuery();
        if(rst.next()){
            return rst.getString(2);
        }
        return null;
    }
    public void loadAllTeachernSubject(ArrayList<AllTeachersSubjectTM> allTeachersSubjectTMS){
        ObservableList<AllTeachersSubjectTM> observableList= FXCollections.observableArrayList();
        allTeachersSubjectTMS.forEach(e->{observableList.add(new AllTeachersSubjectTM(e.getTeacherID(),e.getTeacherName(),
                e.getSunjectID(),e.getSubjectName(),e.getSubjectMedium()));});
        tblAllTeacherSubject.setItems(observableList);
    }

    public void backOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../View/TeacherManageForm.fxml");
        Parent load = FXMLLoader.load(resource);
        addSubjectToTeacherContext.getChildren().clear();
        addSubjectToTeacherContext.getChildren().add(load);
    }
}
