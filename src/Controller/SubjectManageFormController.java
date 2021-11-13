package Controller;

import Controller.EntityController.SubjectController;
import Model.Subject;
import Model.Teacher;
import Util.AlertMassage;
import Util.ValidationUtil;
import View.TM.SubjectTM;
import View.TM.TeacherTM;
import com.jfoenix.animation.alert.JFXAlertAnimation;
import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class SubjectManageFormController {
    public TextField txtSubjectID;
    public TextField txtSubjectName;
    public JFXComboBox cmbMedium;
    public Button btnSave;
    public AnchorPane subjectManagementController;
    public TableView tblSubject;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colMeadium;
    public AnchorPane SubjectManageContext;


    LinkedHashMap<TextField, Pattern> allValidations = new LinkedHashMap<>();
    Pattern idPattern = Pattern.compile("^(Subject-)[0-9]{1,}$");
    Pattern namePattern = Pattern.compile("^[A-z ]{3,20}$");

    public void initialize(){

        colId.setCellValueFactory(new PropertyValueFactory<>("subjectID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("subjectName"));
        colMeadium.setCellValueFactory(new PropertyValueFactory<>("subjectMedium"));

        cmbMedium.getItems().addAll("Sinhala","English","Tamil");
        btnSave.setDisable(true);
        setAllValidations();

        try {
            setSubject(new SubjectController().getAllSubject());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void SaveOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Subject subject=new Subject(txtSubjectID.getText(),txtSubjectName.getText(),(String) cmbMedium.getValue());

        if(new SubjectController().saveSubject(subject)) {
           new AlertMassage().successMassage("Save Successful");
            setSubject(new SubjectController().getAllSubject());
        }else {
          new AlertMassage().errorMassage("Try Again");
        }
    }

    public void setAllValidations(){
        allValidations.put(txtSubjectID,idPattern);
        allValidations.put(txtSubjectName,namePattern);
    }

    public void Key_Released(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(allValidations,btnSave);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                new Alert(Alert.AlertType.INFORMATION, "Aded").showAndWait();
            }
        }
    }

    public void subjectEditandDeleteOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../View/SubjectEditAndDeleteForm.fxml");
        Parent load = FXMLLoader.load(resource);
        SubjectManageContext.getChildren().clear();
        SubjectManageContext.getChildren().add(load);

    }

    public void setSubject(ArrayList<Subject> teachers){
        ObservableList<SubjectTM> observableList= FXCollections.observableArrayList();
        teachers.forEach(e->{observableList.add(new SubjectTM(e.getSubjectID(),e.getSubjectName(),e.getSubjectMedium()));});
        tblSubject.setItems(observableList);
    }


}
