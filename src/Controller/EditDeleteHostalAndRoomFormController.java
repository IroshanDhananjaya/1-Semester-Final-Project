package Controller;

import Controller.EntityController.RoomAndHostalController;
import Model.Hostal;
import Model.Room;
import Util.AlertMassage;
import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;

public class EditDeleteHostalAndRoomFormController {
    public JFXComboBox cmbRoomIds;
    public JFXComboBox cmbHostalIdsRoom;
    public TextField txtRoomDetails;
    public JFXComboBox cmbNoOfBed;
    public JFXComboBox cmbHostalID;
    public JFXComboBox cmbNoOfRoom;
    public TextField txtHostalName;
    public JFXComboBox cmbHostalType;
    public TextField txtContact;
    public AnchorPane editandDeleteContext;

    public void initialize(){

        cmbHostalType.getItems().addAll("Boys","Girls");
        cmbNoOfRoom.getItems().addAll("1","2","3","4","5","6","7","8","9","10");
        cmbNoOfBed.getItems().addAll("1","2","3","4","5","6","7","8","9","10");
        try {
            loadRoomIDs();
            loadHostalIDs();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        cmbHostalID.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
                    try {
                       setHostalData(new RoomAndHostalController().getHostal((String)newValue));
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                });
        cmbRoomIds.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
                    try {
                      setRoomDate(new RoomAndHostalController().getRoom((String)newValue));
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                });
    }

    public void editRoomOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Room room=new Room((String) cmbRoomIds.getValue(),(String) cmbHostalIdsRoom.getValue(),txtRoomDetails.getText(),
                Integer.parseInt((String) cmbNoOfBed.getValue()));

        if(new RoomAndHostalController().updateRoom(room))
           new AlertMassage().successMassage("Update Successful");
        else
            new Alert(Alert.AlertType.WARNING,"Try Again").show();
    }

    public void editHostalOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Hostal hostal=new Hostal((String) cmbHostalID.getValue(),txtHostalName.getText(),(String) cmbHostalType.getValue(),
                Integer.parseInt((String) cmbNoOfRoom.getValue()),txtContact.getText());

        if(new RoomAndHostalController().updateHostal(hostal))
            new AlertMassage().successMassage("Update Successful");
        else
          new AlertMassage().errorMassage("Try Again");
    }

    public void deleteRoomOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        if(new RoomAndHostalController().deleteRoom((String) cmbRoomIds.getValue()))
            new AlertMassage().successMassage("Delete Successful");
        else
            new AlertMassage().errorMassage("Try Again");
    }

    public void deleteHostalOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        if(new RoomAndHostalController().deleteHostal((String) cmbHostalID.getValue()))
            new AlertMassage().successMassage("Delete Successful");
        else
            new AlertMassage().errorMassage("Try Again");
    }

    public void loadRoomIDs() throws SQLException, ClassNotFoundException {
        List<String>roomIds=new RoomAndHostalController().getRoomIds();
        cmbRoomIds.getItems().addAll(roomIds);
    }

    public void loadHostalIDs() throws SQLException, ClassNotFoundException {
        List<String>roomIds=new RoomAndHostalController().getHostalIDs();
        cmbHostalID.getItems().addAll(roomIds);
        cmbHostalIdsRoom.getItems().addAll(roomIds);
    }

    public void setRoomDate(Room room){
        cmbHostalIdsRoom.setValue(room.getHostalID());
        txtRoomDetails.setText(room.getDetails());
        cmbNoOfBed.setValue(String.valueOf(room.getNoOfBed()));
    }

    public void setHostalData(Hostal hostal){

        txtHostalName.setText(hostal.getHostalName());
        cmbHostalType.setValue(hostal.getHostalType());
        cmbNoOfRoom.setValue(String.valueOf(hostal.getNoOfRoom()));
        txtContact.setText(hostal.getHostalContact());

    }

    public void backOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../View/HostalManageForm.fxml");
        Parent load = FXMLLoader.load(resource);
        editandDeleteContext.getChildren().clear();
        editandDeleteContext.getChildren().add(load);
    }
}
