package Controller;

import Controller.EntityController.RoomAndHostalController;
import Model.Room;
import View.TM.RoomTM;
import View.TM.TeacherTM;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;

public class HostalManageFormController {
    public AnchorPane HostalManageContext;
    public JFXComboBox cmbRoomIds;
    public AnchorPane addStudenForRoomContext;
    public TableView tblRooms;
    public TableColumn colHostalID;
    public TableColumn colRoomID;
    public TableColumn colRoomDetails;
    public TableColumn colNoOfBed;

    public void initialize(){
        colHostalID.setCellValueFactory(new PropertyValueFactory<>("hostalID"));
        colRoomID.setCellValueFactory(new PropertyValueFactory<>("roomID"));
        colRoomDetails.setCellValueFactory(new PropertyValueFactory<>("details"));
        colNoOfBed.setCellValueFactory(new PropertyValueFactory<>("noOfBed"));

        try {
            loadAllRooms(new RoomAndHostalController().getRoom());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void addRoomandHostalOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../View/AddNewRoomandHostalForm.fxml");
        Parent load = FXMLLoader.load(resource);
        HostalManageContext.getChildren().clear();
        HostalManageContext.getChildren().add(load);
    }

    public void addStudentForRoomOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../View/AddStudentForRoomForm.fxml");
        Parent load = FXMLLoader.load(resource);
        HostalManageContext.getChildren().clear();
        HostalManageContext.getChildren().add(load);
    }

    public void editandDeleteHostalAndRoomOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../View/EditDeleteHostalAndRoomForm.fxml");
        Parent load = FXMLLoader.load(resource);
        HostalManageContext.getChildren().clear();
        HostalManageContext.getChildren().add(load);
    }

    public void loadAllRooms(ArrayList<Room> rooms){
        ObservableList<RoomTM> observableList= FXCollections.observableArrayList();
        rooms.forEach(e->{observableList.add(new RoomTM(e.getRoomID(),e.getHostalID(),e.getDetails(),e.getNoOfBed()));});
        tblRooms.setItems(observableList);
    }

}
