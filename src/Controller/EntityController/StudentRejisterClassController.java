package Controller.EntityController;

import DB.DbConnection;
import Model.StudentRejisterClassRoom;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentRejisterClassController{

    public boolean saveRejistration(StudentRejisterClassRoom sr1) throws SQLException, ClassNotFoundException {
        PreparedStatement stm1=DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `Student " +
                "Rejistration` WHERE StudentIndexNumber=?");
        stm1.setString(1,sr1.getStudentIndexNumber());

        ResultSet rst=stm1.executeQuery();
        if(rst.next()){
            return false;
        }

        Connection con= DbConnection.getInstance().getConnection();
        String query="INSERT INTO `Student Rejistration` VALUES(?,?,?)";
        PreparedStatement stm =con.prepareStatement(query);
        stm.setObject(1,sr1.getClassID());
        stm.setObject(2,sr1.getStudentIndexNumber());
        stm.setObject(3,sr1.getRejisterDate());

        return stm.executeUpdate()>0;
    }

}
