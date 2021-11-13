package Controller;

import Controller.EntityController.ClassAndSectionController;
import Controller.EntityController.SportController;
import Controller.EntityController.StudentController;
import Controller.EntityController.StudentRejisterClassController;
import DB.DbConnection;
import Model.StudentRejisterClassRoom;
import Util.AlertMassage;
import com.jfoenix.controls.JFXDatePicker;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class StudentAddforClassFormController {
    public ComboBox cmbStudentID;
    public ComboBox cmbClassID;
    public TableView tblStudent;
    public TableColumn colStudentName;
    public TextField txtRejisterID;
    public Label lblAlreadyAdded;
    public ComboBox cmbYear;
    public ComboBox cmbMonth;
    public ComboBox cmbDay;
    public AnchorPane admisionStudentContext;
    public Label lblClassName;
    public Label lblStudentName;
    public JFXDatePicker datePicker;

    public void initialize(){



        try {
            loadStudentIds();
            loadClassIds();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        cmbStudentID.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
                    try {
                        getStudentName((String)newValue);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                });
        cmbClassID.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
                    try {
                        getClassName((String)newValue);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                });
    }

    public void addToClassAction(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {

        StudentRejisterClassRoom studentRejisterClassRoom=new StudentRejisterClassRoom((String) cmbClassID.getValue()
                ,(String) cmbStudentID.getValue(),datePicker.getValue().toString());


        if(new StudentRejisterClassController().saveRejistration(studentRejisterClassRoom))
           new AlertMassage().successMassage("Successful Added");
        else
           new AlertMassage().errorMassage("This student already added");

    }


    private void loadStudentIds() throws SQLException, ClassNotFoundException {
        List<String> StudentIDs = new StudentController().getStudentIDs();
        cmbStudentID.getItems().addAll(StudentIDs);
    }
    private void loadClassIds() throws SQLException, ClassNotFoundException {
        List<String> classIDs = new ClassAndSectionController().getClassIDs();
        cmbClassID.getItems().addAll(classIDs);
    }

    public void cancelOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../View/SectionClassroomManageForm.fxml");
        Parent load = FXMLLoader.load(resource);
        admisionStudentContext.getChildren().clear();
        admisionStudentContext.getChildren().add(load);
    }
    public void getStudentName(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm= DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Student " +
                "WHERE StudentIndexNumber=?");
        stm.setObject(1,id);

        ResultSet rst=stm.executeQuery();
        if(rst.next()){
            lblStudentName.setText(rst.getString(2)+" "+rst.getString(3));
        }
    }
    public void getClassName(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm= DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Class " +
                "WHERE ClassID=?");
        stm.setObject(1,id);

        ResultSet rst=stm.executeQuery();
        if(rst.next()){
            lblClassName.setText(rst.getString(2));
        }
    }
}
