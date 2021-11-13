package Controller;

import Controller.EntityController.TeacherController;
import Model.Teacher;
import View.TM.TeacherTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;

public class TeacherManageFormController {
    public AnchorPane TeacherManagementContext;
    public TableView tblTeacher;
    public TableColumn colID;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colGender;
    public TableColumn colReligion;
    public TableColumn colContact;

    public void initialize(){
        colID.setCellValueFactory(new PropertyValueFactory<>("teacherID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("teacherName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        colReligion.setCellValueFactory(new PropertyValueFactory<>("religion"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        try {
            setTeachers(new TeacherController().getAllTeacher());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void addNewTeacherOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../View/AddNewTeacherForm.fxml");
        Parent load = FXMLLoader.load(resource);
        TeacherManagementContext.getChildren().clear();
        TeacherManagementContext.getChildren().add(load);
    }

    public void TeacherDetailsOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../View/TeacherDetailsForm.fxml");
        Parent load = FXMLLoader.load(resource);
        TeacherManagementContext.getChildren().clear();
        TeacherManagementContext.getChildren().add(load);
    }

    public void setTeachers(ArrayList<Teacher> teachers){
        ObservableList<TeacherTM>observableList= FXCollections.observableArrayList();
        teachers.forEach(e->{observableList.add(new TeacherTM(e.getTeacherID(),e.getTeacherName(),e.getAddress(),
                e.getGender(),e.getReligion(),e.getContact()));});
        tblTeacher.setItems(observableList);
    }

    public void addSubjectOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../View/AddSubjectForTeacherForm.fxml");
        Parent load = FXMLLoader.load(resource);
        TeacherManagementContext.getChildren().clear();
        TeacherManagementContext.getChildren().add(load);
    }
}
