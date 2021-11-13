package Controller;

import DB.DbConnection;
import Util.AlertMassage;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author : Dhananjaya
 * @since : 0.0.1
 **/
public class LoginPageFormController {
    public AnchorPane LoginDashboardContext;
    public JFXTextField txtUserName;
    public JFXPasswordField txtPassword;

    public static String name;
    public static String userType;

    public void btnLoginOnAction(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
      logingControll();
    }

  /*  public void btnLoginOnAction111(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../View/OtherUserDashBoardForm.fxml");
        Parent load = FXMLLoader.load(resource);
        LoginDashboardContext.getChildren().clear();
        LoginDashboardContext.getChildren().add(load);
    }*/

    public void logingControll() throws SQLException, ClassNotFoundException, IOException {
        String userName = txtUserName.getText();
        String password = txtPassword.getText();

        Connection con= DbConnection.getInstance().getConnection();
        PreparedStatement stm=null;
        stm=con.prepareStatement("SELECT * FROM `User` WHERE userName=? AND userPW=md5(?)");
        stm.setString(1,userName);
        stm.setString(2,password);

        ResultSet resultSet = stm.executeQuery();
        if(resultSet.next()){
            name=resultSet.getString(2);
            userType=resultSet.getString(4);
            URL resource = getClass().getResource(userType.equals("admin")? "../View/DashboardForm.fxml": "../View" +
                    "/OtherUserDashBoardForm.fxml");
            Parent load = FXMLLoader.load(resource);
            LoginDashboardContext.getChildren().clear();
            LoginDashboardContext.getChildren().add(load);

        }else {
            new AlertMassage().errorMassage("Password or User Name Incorrect");
        }
    }
}
