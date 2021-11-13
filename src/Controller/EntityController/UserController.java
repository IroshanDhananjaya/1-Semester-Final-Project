package Controller.EntityController;

import DB.DbConnection;
import Model.User;
import sun.nio.cs.US_ASCII;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;



public class UserController {
    public boolean addUsers(User user) throws SQLException, ClassNotFoundException {
        PreparedStatement stm1=DbConnection.getInstance().getConnection().prepareStatement("SELECT 1 FROM `User` " +
                "WHERE userNIC=? ");
        stm1.setString(1,user.getUserNIC());
        ResultSet rst=stm1.executeQuery();
        if(rst.next()){
            return false;
        }

        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO `User` VALUES" +
                " (?,?,md5(?),?)");
        stm.setString(1, user.getUserNIC());
        stm.setString(2, user.getUserName());
        stm.setString(3, user.getUserPw());
        stm.setString(4, user.getUserType());
        return stm.executeUpdate() > 0;
    }

    public boolean updateUser(User user) throws SQLException, ClassNotFoundException {
        PreparedStatement statement = DbConnection.getInstance().getConnection().prepareStatement("UPDATE `User` SET " +
                "userName=?,userPW=?,userType=? WHERE userNIC=?");

        statement.setString(1,user.getUserName());
        statement.setString(2,user.getUserPw());
        statement.setString(3,user.getUserType());
        statement.setString(4,user.getUserNIC());

        return statement.executeUpdate()>0;
    }

    public boolean deleteUser(String nic) throws SQLException, ClassNotFoundException {
        if(DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM `User` WHERE userNIC='"+nic+"'").executeUpdate()>0){
            return true;
        }else{
            return false;
        }
    }

    public ArrayList<User> getAllUser() throws SQLException, ClassNotFoundException {
        PreparedStatement stm=DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `User`");
        ArrayList<User> users=new ArrayList<>();
        ResultSet rst=stm.executeQuery();

        while (rst.next()){
            users.add(new User(rst.getString(1),rst.getString(2),rst.getString(3),rst.getString(4)));
        }
        return users;
    }

}
