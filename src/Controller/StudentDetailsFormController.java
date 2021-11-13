package Controller;

import Controller.EntityController.ClassAndSectionController;
import Controller.EntityController.StudentController;
import Controller.EntityController.SubjectController;
import DB.DbConnection;
import Model.Students;
import Util.AlertMassage;
import View.TM.StudentDetailsInClassTM;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class StudentDetailsFormController {

    public ComboBox cmbStudentIndex;
    public Label lblPhoto;
    public ImageView imgMale;
    public ImageView imgFemale;
    public Label lblStudentName;
    public Label lblGender;
    public Label lblReligion;
    public Label lblBirthDay;
    public Label lblMotherName;
    public Label lblFartherName;
    public Label lblFartherOccupation;
    public Label lblAddress;
    public Label lblContact;
    public AnchorPane StudentDetailsContext;

    public Label lblStudentClass;
    public Label lblStudentClassID;


    public void initialize(){
        try {
            loadStudentIds();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        cmbStudentIndex.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
                    try {
                        setData((String) newValue);
                        getStudentsClass((String)newValue);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                });
    }

    public void setData(String id) throws SQLException, ClassNotFoundException {
       Students students=new StudentController().getStudent(id);
       lblStudentName.setText(students.getStudentFirstName()+" "+students.getStudentLastName());
       lblAddress.setText(students.getAddress());
       lblBirthDay.setText(students.getBirthDay());
       lblContact.setText(students.getContact());
       lblFartherName.setText(students.getFatherFirstName()+" "+students.getFartherLastName());
       lblMotherName.setText(students.getMotherFirstName()+" "+students.getMotherLastName());
       lblFartherOccupation.setText(students.getFartherOccupation());
       lblReligion.setText(students.getReligion());
       lblGender.setText(students.getGender());
       if(lblGender.getText().equals("Male")){
           lblPhoto.setVisible(false);
           imgMale.setVisible(true);
           imgFemale.setVisible(false);
       }else{
           lblPhoto.setVisible(false);
           imgMale.setVisible(false);
           imgFemale.setVisible(true);
       }

    }
    private void loadStudentIds() throws SQLException, ClassNotFoundException {
        List<String> StudentIDs = new StudentController().getStudentIDs();
        cmbStudentIndex.getItems().addAll(StudentIDs);
    }

    public void StudentEditOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../View/StudentEditForm.fxml");
        Parent load = FXMLLoader.load(resource);
        StudentDetailsContext.getChildren().clear();
        StudentDetailsContext.getChildren().add(load);
    }

    public void StudentDeleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if(new StudentController().deleteStudent((String) cmbStudentIndex.getValue()))
            new AlertMassage().successMassage("Delete Successful");
        else
          new AlertMassage().errorMassage("Try Again");
    }


    public void backOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../View/StudentManageForm.fxml");
        Parent load = FXMLLoader.load(resource);
        StudentDetailsContext.getChildren().clear();
        StudentDetailsContext.getChildren().add(load);
    }

    public void getStudentsClass(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm= DbConnection.getInstance().getConnection().prepareStatement("SELECT * From `Student " +
                "Rejistration` WHERE StudentIndexNumber=?");
        stm.setObject(1,id);
        ResultSet rst=stm.executeQuery();
        while (rst.next()){
            PreparedStatement stm1=DbConnection.getInstance().getConnection().prepareStatement("Select * FROM Class " +
                    "WHERE " +
                    "ClassID=?");
            stm1.setObject(1,rst.getString(1));
            ResultSet rst1=stm1.executeQuery();
            if(rst1.next()){
                lblStudentClassID.setText(rst.getString(1));
                lblStudentClass.setText(rst1.getString(2));
            }
        }
    }

    public void studentRemoveFromClass(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if(new ClassAndSectionController().deleteClassFromStudent((String) cmbStudentIndex.getValue(),lblStudentClassID.getText())){
            new AlertMassage().successMassage("Delete Successful");
        } else {
            new AlertMassage().errorMassage("Try Again");
        }
    }

    public void printDetailsOnAction(ActionEvent actionEvent) {
        try {
            /*Create a report using table data*/
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/View/report/StudentDetails" +
                    ".jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(design);
            /*Get all values from table*/

            HashMap map=new HashMap();
            map.put("StudentName",(String)lblStudentName.getText());
            map.put("gender",(String)lblGender.getText());
            map.put("religion",(String)lblReligion.getText());
            map.put("birthDay",(String)lblBirthDay.getText());
            map.put("motherName",(String)lblMotherName.getText());
            map.put("fartherName",(String)lblFartherName.getText());
            map.put("occupation",(String)lblFartherOccupation.getText());
            map.put("address",(String)lblAddress.getText());
            map.put("contact",(String)lblContact.getText());
            map.put("class",(String)lblStudentClass.getText());

            /*Create a Bean Array Data Source and pass the table values to it*/
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, map,new JREmptyDataSource(1));
            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }
}
