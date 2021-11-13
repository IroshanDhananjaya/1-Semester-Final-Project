package Controller;

import Controller.EntityController.StudentController;
import Controller.EntityController.SubjectController;
import Model.Subject;
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

public class SubjectEditAndDeleteFormController {
    public JFXComboBox cmbSubjectID;
    public JFXTextField txtSubjectName;
    public JFXComboBox cmbMedium;
    public AnchorPane SubjectEditandDeleteContext;

    public void initialize(){

        cmbMedium.getItems().addAll("Sinhala","English","Tamil");
        try {
            loadSubjectIds();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        cmbSubjectID.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
                    try {
                        setData(new SubjectController().getSubject((String) newValue));
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                });
    }


    public void editOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Subject subject=new Subject((String) cmbSubjectID.getValue(),txtSubjectName.getText(),(String) cmbMedium.getValue());
        if(new SubjectController().updateSubject(subject))
           new AlertMassage().successMassage("Update Successful");
        else
          new AlertMassage().errorMassage("Try Again");
    }

    public void deleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if(new SubjectController().deleteSubject((String) cmbSubjectID.getValue()))
            new AlertMassage().successMassage("Delete Successful");
        else
            new AlertMassage().errorMassage("Try Again");
    }

    public void setData(Subject subject){
        txtSubjectName.setText(subject.getSubjectName());
        cmbMedium.setValue(subject.getSubjectMedium());
    }

    private void loadSubjectIds() throws SQLException, ClassNotFoundException {
        List<String> SubjectIDs = new SubjectController().getSubjectId();
        cmbSubjectID.getItems().addAll(SubjectIDs);
    }

    public void cancelOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../View/SubjectManageForm.fxml");
        Parent load = FXMLLoader.load(resource);
        SubjectEditandDeleteContext.getChildren().clear();
        SubjectEditandDeleteContext.getChildren().add(load);
    }
}
