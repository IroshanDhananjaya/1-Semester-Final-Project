package Controller;

import Controller.EntityController.SubjectController;
import Controller.EntityController.TeacherController;
import Model.Teacher;
import Util.AlertMassage;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;

public class TeacherEditFormController {
    public JFXComboBox cmbTeacherID;
    public JFXComboBox cmbGender;
    public JFXTextField txtTeacherName;
    public JFXTextField txtAddress;
    public JFXComboBox cmbReligion;
    public JFXTextField cmbContact;
    public AnchorPane teacherEditContext;

    public void initialize(){
        cmbGender.getItems().addAll("Male","Female");
        cmbReligion.getItems().addAll("Buddhism","Muslim","Tamil");

        try {
            setTeachersID();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        cmbTeacherID.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
                    try {
                        setTeacherData(new TeacherController().getTeacher((String)newValue));

                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                });
    }

    public void editOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Teacher teacher=new Teacher((String)cmbTeacherID.getValue(),txtTeacherName.getText(),txtAddress.getText(),
                (String) cmbGender.getValue(),(String)cmbReligion.getValue(),cmbContact.getText());

        if(new TeacherController().updateTeacher(teacher))
            new AlertMassage().successMassage("Update Successful");
        else
         new AlertMassage().errorMassage("Try Again");
    }

    public void cancelOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../View/TeacherDetailsForm.fxml");
        Parent load = FXMLLoader.load(resource);
        teacherEditContext.getChildren().clear();
        teacherEditContext.getChildren().add(load);
    }
    public void setTeacherData(Teacher teacher){
        txtTeacherName.setText(teacher.getTeacherName());
        txtAddress.setText(teacher.getAddress());
        cmbContact.setText(teacher.getContact());
        cmbGender.setValue(teacher.getGender());
        cmbReligion.setValue(teacher.getReligion());
    }
    public void setTeachersID() throws SQLException, ClassNotFoundException {
        List<String> teachersIDs=new TeacherController().getTeacherIDs();
        cmbTeacherID.getItems().addAll(teachersIDs);
    }
}
