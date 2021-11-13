package Controller;

import Controller.EntityController.StudentController;
import Model.Students;
import Util.AlertMassage;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;

public class StudentEditFormController {
    public TextField txtStudentIndexNumber;
    public TextField txtStudentFirstName;
    public TextField txtStudentLastName;
    public TextField txtMotherFirstName;
    public TextField txtMortherLastName;
    public TextField txtFartherFirstName;
    public TextField txtAddress;
    public TextField txtFartherOccupation;
    public TextField txtFartherLastName;
    public TextField txtContact;
    public Button savebtn;
    public ComboBox cmbBirthYear;
    public ComboBox cmbBirthMonth;
    public ComboBox cmbBirthDay;
    public ComboBox cmbGender;
    public ComboBox cmbReligion;
    public ComboBox cmbStudents;
    public TextField txtBirthDay;
    public AnchorPane studentEditContext;


    public void initialize() {

        try {
            loadStudentIds();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        cmbGender.getItems().addAll("Male", "Female");
        cmbReligion.getItems().addAll("Buddhism", "Muslim", "Tamil");


        cmbStudents.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
                    try {
                        setDate(new StudentController().getStudent((String) newValue));
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                });
    }


    public void studentSaveOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {


        Students student = new Students((String) cmbStudents.getValue(), txtStudentFirstName.getText(),
                txtStudentLastName.getText(), (String) cmbGender.getValue(), (String) cmbReligion.getValue(),
                txtBirthDay.getText(),
                txtMotherFirstName.getText(), txtMortherLastName.getText(), txtFartherFirstName.getText(),
                txtFartherLastName.getText(), txtFartherOccupation.getText(), txtAddress.getText(), txtContact.getText());

        if (new StudentController().updateStudent(student))
            new AlertMassage().successMassage("Update Successful");
        else
            new AlertMassage().errorMassage("Try Again");
    }


    private void loadStudentIds() throws SQLException, ClassNotFoundException {
        List<String> StudentIDs = new StudentController().getStudentIDs();
        cmbStudents.getItems().addAll(StudentIDs);
    }

    public void setDate(Students s) {
        txtStudentFirstName.setText(s.getStudentFirstName());
        txtStudentLastName.setText(s.getStudentLastName());
        txtAddress.setText(s.getAddress());
        txtContact.setText(s.getContact());
        txtFartherFirstName.setText(s.getFatherFirstName());
        txtFartherLastName.setText(s.getFartherLastName());
        txtFartherOccupation.setText(s.getFartherOccupation());
        txtMotherFirstName.setText(s.getMotherFirstName());
        txtMortherLastName.setText(s.getMotherLastName());
        txtBirthDay.setText(s.getBirthDay());
        cmbGender.setValue(s.getGender());
        cmbReligion.setValue(s.getReligion());
    }

    public void cancelOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../View/StudentDetailsForm.fxml");
        Parent load = FXMLLoader.load(resource);
        studentEditContext.getChildren().clear();
        studentEditContext.getChildren().add(load);
    }
}
