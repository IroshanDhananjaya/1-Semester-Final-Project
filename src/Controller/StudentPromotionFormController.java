package Controller;

import Controller.EntityController.ClassAndSectionController;
import DB.DbConnection;
import Util.AlertMassage;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class StudentPromotionFormController {
    public ComboBox cmbFromClass;
    public ComboBox cmbToclass;
    public Label lblFromClass;
    public Label lbltoClass1;
    public AnchorPane studentPromotCpntext;

    public void initialize(){
        try {
            loadClassIds();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        cmbToclass.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
                    try {
                       getToClassDate((String) newValue);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                });
        cmbFromClass.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
                    try {
                        getFromClassDate((String) newValue);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                });
    }

    public void saveOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if(promoteStudent((String)cmbFromClass.getValue(),(String) cmbToclass.getValue()))
          new AlertMassage().successMassage("promotion Successful");
        else
          new AlertMassage().errorMassage("Try Again");
    }

    public boolean promoteStudent(String fromClassID,String toClass) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("UPDATE `Student " +
                "Rejistration` SET ClassID=? WHERE ClassID=?");

        stm.setObject(1,toClass);
        stm.setObject(2,fromClassID);
         return stm.executeUpdate()>0;
    }
    private void loadClassIds() throws SQLException, ClassNotFoundException {
        List<String> classIDs = new ClassAndSectionController().getClassIDs();
        cmbFromClass.getItems().addAll(classIDs);
        cmbToclass.getItems().addAll(classIDs);
    }

    public void getFromClassDate(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm=DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Class WHERE ClassID=?");

        stm.setObject(1,id);
        ResultSet rst=stm.executeQuery();
        if(rst.next()){
           lblFromClass.setText(rst.getString(2));
        }

    }
    public void getToClassDate(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm=DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Class WHERE ClassID=?");

        stm.setObject(1,id);
        ResultSet rst=stm.executeQuery();
        if(rst.next()){
            lbltoClass1.setText(rst.getString(2));
        }

    }

    public void backOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../View/StudentManageForm.fxml");
        Parent load = FXMLLoader.load(resource);
        studentPromotCpntext.getChildren().clear();
        studentPromotCpntext.getChildren().add(load);
    }
}
