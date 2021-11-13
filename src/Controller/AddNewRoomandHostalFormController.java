package Controller;

import Controller.EntityController.RoomAndHostalController;
import DB.DbConnection;
import Model.Hostal;
import Model.Room;
import Util.AlertMassage;
import Util.ValidationUtil;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

public class AddNewRoomandHostalFormController {
    public TextField txtRoomID;
    public JFXComboBox cmbHostal;
    public TextField txtRoomDetails;
    public JFXComboBox cmbNoOdBed;
    public TextField txtHostalID;
    public JFXComboBox cmbHostalType;
    public TextField txtHostalName;
    public JFXComboBox cmbNoOfRoom;
    public TextField txtHostalContact;
    public AnchorPane AddClassandContext;
    public Button btnRoomAdd;
    public Button btnHostalAdd;

    LinkedHashMap<TextField, Pattern> allValidations = new LinkedHashMap<>();
    LinkedHashMap<TextField, Pattern> allValidations1 = new LinkedHashMap<>();
    Pattern idPatternRoom = Pattern.compile("^(Room-)[0-9]{3,4}$");
    Pattern idPatternHostal = Pattern.compile("^(Hostal-)[0-9]{3,4}$");
    Pattern namePatternHostal = Pattern.compile("^[A-z ]{3,20}$");
    Pattern contactPattern = Pattern.compile("^(0)[0-9][-]?[0-9]{8}$");

    public void initialize(){

        btnHostalAdd.setDisable(true);
        btnRoomAdd.setDisable(true);

        AllValidate();
        AllValidateHostal();

        cmbHostalType.getItems().addAll("Boys","Girls");
        cmbNoOfRoom.getItems().addAll("1","2","3","4","5","6","7","8","9","10");
        cmbNoOdBed.getItems().addAll("1","2","3","4","5","6","7","8","9","10");

        try {
            loadHostalIds();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void roomAddOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Room R1=new Room(txtRoomID.getText(),(String) cmbHostal.getValue(),txtRoomDetails.getText(),
                Integer.parseInt((String) cmbNoOdBed.getValue()));

        if(new RoomAndHostalController().saveRoom(R1)) {
           new AlertMassage().successMassage("Successful Added");
        } else {
           new AlertMassage().errorMassage("Room ID already added");
        }
    }

    public void hostalAddOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Hostal h1=new Hostal(txtHostalID.getText(),txtHostalName.getText(),(String)cmbHostalType.getValue(),
                Integer.parseInt((String)cmbNoOfRoom.getValue()),txtHostalContact.getText());

        if(new RoomAndHostalController().saveHostal(h1)) {
            cmbHostal.getItems().addAll(h1.getHostalID());
            new AlertMassage().successMassage("Successful Added");
        } else{
            new AlertMassage().errorMassage("Section ID already added");
        }
    }
    private void loadHostalIds() throws SQLException, ClassNotFoundException {

        List<String> hostalIds = new RoomAndHostalController().getHostalIDs();
        cmbHostal.getItems().addAll(hostalIds);

    }

    public void backToOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../View/HostalManageForm.fxml");
        Parent load = FXMLLoader.load(resource);
        AddClassandContext.getChildren().clear();
        AddClassandContext.getChildren().add(load);
    }
    public void AllValidate(){
        allValidations.put(txtRoomID,idPatternRoom);
    }
    public void AllValidateHostal(){
        allValidations1.put(txtHostalID,idPatternHostal);
        allValidations1.put(txtHostalName,namePatternHostal);
        allValidations1.put(txtHostalContact,contactPattern);
    }

    public void Room_KeyRelesed(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(allValidations, btnRoomAdd);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                new Alert(Alert.AlertType.INFORMATION, "Aded").showAndWait();
            }
        }
    }

    public void hostalKeyRelese(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(allValidations1,btnHostalAdd);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                new Alert(Alert.AlertType.INFORMATION, "Aded").showAndWait();
            }
        }
    }

}
