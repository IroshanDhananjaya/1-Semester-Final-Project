package Controller;

import Controller.EntityController.ClassAndSectionController;
import Model.ClassRoom;
import Model.Teacher;
import View.TM.ClassRoomTM;
import View.TM.TeacherTM;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;

public class SectionClassroomManageFormController {
    public AnchorPane ClassManageContext;
    public AnchorPane addNewStudentContect;
    public AnchorPane addSectionContext;
    public TableView tblClassDetails;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colSection;
    public TableColumn colDetails;

    public void initialize(){
        colId.setCellValueFactory(new PropertyValueFactory<>("classID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("className"));
        colSection.setCellValueFactory(new PropertyValueFactory<>("section"));
        colDetails.setCellValueFactory(new PropertyValueFactory<>("classDetails"));

        try {
            loadAllClass(new ClassAndSectionController().getAllClass());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void admissionofStudentOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../View/StudentAddforClassForm.fxml");
        Parent load = FXMLLoader.load(resource);
        ClassManageContext.getChildren().clear();
        ClassManageContext.getChildren().add(load);
    }



    public void editClassOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../View/EditClassDetailsForm.fxml");
        Parent load = FXMLLoader.load(resource);
        ClassManageContext.getChildren().clear();
        ClassManageContext.getChildren().add(load);
    }

    public void addNewClassOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../View/ClassRoomMaitainForm.fxml");
        Parent load = FXMLLoader.load(resource);
        ClassManageContext.getChildren().clear();
        ClassManageContext.getChildren().add(load);

    }

    public void loadAllClass(ArrayList<ClassRoom> classRooms){
        ObservableList<ClassRoomTM> observableList= FXCollections.observableArrayList();
        classRooms.forEach(e->{observableList.add(new ClassRoomTM(e.getClassID(),e.getClassName(),e.getSectionID(),
                e.getClassDetails()));});
        tblClassDetails.setItems(observableList);
    }


    public void viewStudentDetailsinClassOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../View/StudentDetailsInClassesForm.fxml");
        Parent load = FXMLLoader.load(resource);
        ClassManageContext.getChildren().clear();
        ClassManageContext.getChildren().add(load);
    }
}
