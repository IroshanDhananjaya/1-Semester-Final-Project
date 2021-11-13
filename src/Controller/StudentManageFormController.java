package Controller;

import Controller.EntityController.StudentController;
import Model.Students;
import View.TM.StudentTM;
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

public class StudentManageFormController {
    public AnchorPane StudentManageContext;
    public TableView tblStudent;
    public TableColumn colIndexNumber;
    public TableColumn colName;
    public TableColumn colGender;
    public TableColumn colReligion;
    public TableColumn colbirthDay;
    public TableColumn colAddress;
    public TableColumn colContact;

    public void initialize(){
        colIndexNumber.setCellValueFactory(new PropertyValueFactory<>("studentIndexNumber"));
        colName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        colReligion.setCellValueFactory(new PropertyValueFactory<>("religion"));
        colbirthDay.setCellValueFactory(new PropertyValueFactory<>("birthDay"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));

        try {
            loadAllStudent(new StudentController().getAllStudent());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void StudentRejisterOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../View/StudentRejisterForm.fxml");
        Parent load = FXMLLoader.load(resource);
        StudentManageContext.getChildren().clear();
        StudentManageContext.getChildren().add(load);
    }

    public void StudentDetailsOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../View/StudentDetailsForm.fxml");
        Parent load = FXMLLoader.load(resource);
        StudentManageContext.getChildren().clear();
        StudentManageContext.getChildren().add(load);
    }

    public void StudentPromotionOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../View/StudentPromotionForm.fxml");
        Parent load = FXMLLoader.load(resource);
        StudentManageContext.getChildren().clear();
        StudentManageContext.getChildren().add(load);
    }

    public void loadAllStudent(ArrayList<Students> students){
        ObservableList<StudentTM> observableList= FXCollections.observableArrayList();
        students.forEach(e->{observableList.add(new StudentTM(e.getStudentIndexNumber(),e.getStudentFirstName(),
                e.getStudentLastName(),e.getGender(),e.getReligion(),e.getBirthDay(),e.getMotherFirstName(),
                e.getMotherLastName(),e.getFatherFirstName(),e.getFartherLastName(),e.getFartherOccupation(),
                e.getAddress(),e.getContact()));});
        tblStudent.setItems(observableList);
    }


    public void examDetailsOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../View/StudentExamDetailsForm.fxml");
        Parent load = FXMLLoader.load(resource);
        StudentManageContext.getChildren().clear();
        StudentManageContext.getChildren().add(load);
    }
}
