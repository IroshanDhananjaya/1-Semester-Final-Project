package Controller;

import Controller.EntityController.UserController;
import Model.User;
import Util.AlertMassage;
import View.TM.ExamDetailsTM;
import View.TM.UserTM;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import sun.nio.cs.US_ASCII;

import java.sql.SQLException;
import java.util.ArrayList;

public class addNewUserFormController {
    public AnchorPane addUserContext;
    public JFXTextField txtUserID;
    public JFXTextField txtUserName;
    public JFXTextField txtPassword;
    public JFXComboBox cmbType;
    public TableView<UserTM> tblUser;
    public TableColumn colNIC;
    public TableColumn colName;
    public TableColumn colPassword;
    public TableColumn colType;

    public void initialize(){
        colNIC.setCellValueFactory(new PropertyValueFactory<>("userNIC"));
        colName.setCellValueFactory(new PropertyValueFactory<>("userName"));

        colType.setCellValueFactory(new PropertyValueFactory<>("userType"));

        try {
            loadAllUser(new UserController().getAllUser());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        cmbType.getItems().addAll("admin","Teacher");
    }

    public void addUserOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        User user=new User(txtUserID.getText(),txtUserName.getText(),txtPassword.getText(),(String) cmbType.getValue());

        if(new UserController().addUsers(user)){
            loadAllUser(new UserController().getAllUser());
            new AlertMassage().successMassage("Successful added");
        }else{
            new AlertMassage().errorMassage("NIC Number already added");
        }
    }
    public void loadAllUser(ArrayList<User>user){
        ObservableList<UserTM>observableList= FXCollections.observableArrayList();
        user.forEach(e->{observableList.addAll(new UserTM(e.getUserNIC(),e.getUserName(),e.getUserPw(),
                e.getUserType()));});
        tblUser.setItems(observableList);
    }

    public void tableMouseClicked(MouseEvent mouseEvent) {
        if (tblUser.getSelectionModel().getSelectedItem() != null) {
            UserTM userTM= tblUser.getSelectionModel().getSelectedItem();
           txtUserID.setText(userTM.getUserNIC());
           txtUserName.setText(userTM.getUserName());
           cmbType.setValue(userTM.getUserType());
        } else {
            new AlertMassage().errorMassage("Please Select the row");
        }
    }

    public void editOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        User user=new User(txtUserID.getText(),txtUserName.getText(),txtPassword.getText(),(String) cmbType.getValue());

        if(new UserController().updateUser(user)){
            loadAllUser(new UserController().getAllUser());
            new AlertMassage().successMassage("Update Successful");
        }else{
            new AlertMassage().errorMassage("Please Select the row where the wants to Update");
        }
    }

    public void removeOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if(new UserController().deleteUser(txtUserID.getText())){
            loadAllUser(new UserController().getAllUser());
            new AlertMassage().successMassage("Delete Successful");
        }else{
            new AlertMassage().errorMassage("Please Select the row where the wants to delete");
        }
    }
}
