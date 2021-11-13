package Controller;

import Controller.EntityController.ClassAndSectionController;
import Controller.EntityController.TeacherController;
import DB.DbConnection;
import Model.ClassRoom;
import Util.AlertMassage;
import View.TM.ClassRoomTeachersTM;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
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

public class EditClassDetailsFormController {

    public JFXTextField txtSection;
    public JFXTextField txtClassName;
    public JFXComboBox cmbClassID;
    public JFXTextField txtDetails;
    public JFXComboBox cmbTeacher;
    public TableView tblNewTeacher;
    public TableColumn colNewTeacher;
    public JFXComboBox cmbAddNewTeacher;
    public AnchorPane EditClassContext;

    int cartSelectedRowForRemove=-1;

    public void initialize() throws SQLException, ClassNotFoundException {

        loadClassIds();


        cmbClassID.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                ClassRoom cr=new ClassAndSectionController().getClass((String) newValue);
              setData(cr);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    private void setData(ClassRoom cr) {
        txtClassName.setText(cr.getClassName());
        txtDetails.setText(cr.getClassDetails());
        txtSection.setText(cr.getSectionID());
    }

    private void loadClassIds() throws SQLException, ClassNotFoundException {
        List<String> classIDs = new ClassAndSectionController().getClassIDs();
        cmbClassID.getItems().addAll(classIDs);

    }



    public void saveOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ClassRoom classRoom=new ClassRoom((String) cmbClassID.getValue(),txtClassName.getText(),txtSection.getText(),
                txtDetails.getText());

        if(new ClassAndSectionController().updateClass(classRoom))
            new AlertMassage().successMassage("Update Successful");
        else
           new AlertMassage().errorMassage("Try Again");
    }


    public void RemoveOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if(new ClassAndSectionController().deleteClassRoom((String) cmbClassID.getValue()))
          new AlertMassage().successMassage("Delete Successful");
        else
           new AlertMassage().errorMassage("Try Again");
    }

    public void cancelOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../View/SectionClassroomManageForm.fxml");
        Parent load = FXMLLoader.load(resource);
        EditClassContext.getChildren().clear();
        EditClassContext.getChildren().add(load);
    }
}
