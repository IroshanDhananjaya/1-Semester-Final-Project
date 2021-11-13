package Controller;

import Controller.EntityController.RoomAndHostalController;
import Controller.EntityController.StudentController;
import Controller.EntityController.TeacherController;
import DB.DbConnection;
import Model.StudentRoom;
import Util.AlertMassage;
import View.TM.StudentRoomTM;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddStudentForRoomFormController {
    public AnchorPane addStudenForRoomContext;
    public JFXComboBox cmbStudent;
    public JFXComboBox cmbRoom;
    public JFXTextField txtDate;
    public Label lblName;
    public Label lblRoomName;
       public TableView <StudentRoomTM>tblStudentnRoom;
    public TableColumn colIndexNumber;
    public TableColumn colStudentName;
    public TableColumn colRoomID;
    public TableColumn colHostalID;
    public TableColumn colEntryDate;
    public JFXDatePicker datePicker;
    public Label lblRoomType;


    public void initialize(){

        colIndexNumber.setCellValueFactory(new PropertyValueFactory<>("studentIndex"));
        colStudentName.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        colRoomID.setCellValueFactory(new PropertyValueFactory<>("roomID"));
        colHostalID.setCellValueFactory(new PropertyValueFactory<>("hostalID"));
        colEntryDate.setCellValueFactory(new PropertyValueFactory<>("entryDate"));

        try {
            setAllStudentsRoom(new RoomAndHostalController().getAllStudentandRoomDetails());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }




        try {
            loadRoomIds();
            loadStudentIds();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        cmbStudent.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
                    try {
                       getStudentName((String)newValue);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                });

        cmbRoom.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
                    try {
                       getRoomType((String)newValue);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                });

    }

    public void backOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../View/HostalManageForm.fxml");
        Parent load = FXMLLoader.load(resource);
        addStudenForRoomContext.getChildren().clear();
        addStudenForRoomContext.getChildren().add(load);
    }


    public void addOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {



        StudentRoom studentRoom=new StudentRoom((String) cmbRoom.getValue(),(String)cmbStudent.getValue(),
                datePicker.getValue().toString());
        if(new RoomAndHostalController().addStudentToRoom(studentRoom)) {
            setAllStudentsRoom(new RoomAndHostalController().getAllStudentandRoomDetails());
            new AlertMassage().successMassage("Save Successful");
        } else {
            new AlertMassage().errorMassage("This student already added");
        }
    }
    public void loadStudentIds() throws SQLException, ClassNotFoundException {
        List<String> studentID=new StudentController().getStudentIDs();
        cmbStudent.getItems().addAll(studentID);
    }
    public void loadRoomIds() throws SQLException, ClassNotFoundException {
        List<String> roomID=new RoomAndHostalController().getRoomIds();
        cmbRoom.getItems().addAll(roomID);
    }

    public void getStudentName(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm= DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Student " +
                "WHERE StudentIndexNumber=?");
        stm.setObject(1,id);

        ResultSet rst=stm.executeQuery();
        if(rst.next()){
            lblName.setText(rst.getString(2)+" "+rst.getString(3));
        }
    }


    public void setAllStudentsRoom(ArrayList<StudentRoomTM> studentsRoom){
        ObservableList<StudentRoomTM>studentRoomTMS= FXCollections.observableArrayList();
        studentsRoom.forEach(e->{studentRoomTMS.addAll(new StudentRoomTM(e.getStudentIndex(),e.getStudentName(),
                e.getRoomID(),e.getHostalID(),e.getEntryDate()));});
        tblStudentnRoom.setItems(studentRoomTMS);
    }


    public void deleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if(tblStudentnRoom.getSelectionModel().getSelectedItem()!=null){
            StudentRoomTM studentRoomTM=tblStudentnRoom.getSelectionModel().getSelectedItem();
          if(new RoomAndHostalController().deleteStudentsRoom(studentRoomTM.getStudentIndex())){
              setAllStudentsRoom(new RoomAndHostalController().getAllStudentandRoomDetails());
             new AlertMassage().successMassage("Delete Successful");
          } else {
            new AlertMassage().errorMassage("Try Again");

          }
        }else{
            new AlertMassage().errorMassage("Please Select the row where the wants to remove");
        }
    }
    public void getRoomType(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm=DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Room WHERE " +
                "RoomID='"+id+"'");
        ResultSet rst=stm.executeQuery();
        if(rst.next()){
            PreparedStatement stm1=DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Hostal " +
                    "WHERE HostalID=?");
            stm1.setString(1,rst.getString(2));
            ResultSet rst1=stm1.executeQuery();
            if(rst1.next()){
                lblRoomType.setText(rst1.getString(3));
            }
        }
    }

}
