package Controller.EntityController;

import DB.DbConnection;
import Model.Room;
import Model.Sport;
import Model.StudentandSport;
import Model.Subject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SportController {

    public List<String> getSportIDs() throws SQLException, ClassNotFoundException {
        ResultSet rst=
                DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Sports").executeQuery();
        List<String> ids=new ArrayList<>();
        while (rst.next()){
            ids.add(rst.getString(1));
        }
        return ids;
    }

    public boolean saveSport(Sport s) throws SQLException, ClassNotFoundException {
        PreparedStatement stm;
        Connection con=DbConnection.getInstance().getConnection();
        stm=con.prepareStatement("SELECT 1 FROM Sports " +
                "WHERE " +
                "SportID=?");
        stm.setString(1,s.getSportID());
        ResultSet rst=stm.executeQuery();

        if(!rst.next()) {
            stm = con.prepareStatement("INSERT INTO Sports VALUES (?,?,?)");

            stm.setObject(1, s.getSportID());
            stm.setObject(2, s.getSportName());
            stm.setObject(3, s.getSportType());

            return stm.executeUpdate() > 0;
        }else{
            return false;
        }

    }

    public boolean addStudentIntoSport(StudentandSport studentandSport) throws SQLException, ClassNotFoundException {
        PreparedStatement statement=DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM " +
                "`Student and Sports`WHERE StudentIndexNumber=? AND SportID=?");
        statement.setString(1,studentandSport.getStudentID());
        statement.setString(2,studentandSport.getSportID());
        ResultSet resultSet=statement.executeQuery();
        if(resultSet.next()){
            return false;
        }

        Connection connection=DbConnection.getInstance().getConnection();
        PreparedStatement stm=connection.prepareStatement("INSERT INTO `Student and Sports` VALUES (?,?)");

        stm.setObject(1,studentandSport.getSportID());
        stm.setObject(2,studentandSport.getStudentID());

        return stm.executeUpdate()>0;

    }
    public boolean updateSport(Sport s) throws SQLException, ClassNotFoundException {
        PreparedStatement stm=DbConnection.getInstance().getConnection().prepareStatement("UPDATE Sports SET " +
                "SportName=?, SportType=? WHERE SportID=?");

        stm.setObject(1,s.getSportName());
        stm.setObject(2,s.getSportType());
        stm.setObject(3,s.getSportID());

        return stm.executeUpdate()>0;
    }

    public boolean deleteSport(String id) throws SQLException, ClassNotFoundException {
        if(DbConnection.getInstance().getConnection().prepareStatement("Delete FROM Sports WHERE SportID='"+id+"'").executeUpdate()>0){
            return true;
        }else{
            return false;
        }
    }

    public Sport getSport(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm=DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Sports WHERE" +
                " " +
                "SportID=?");
        stm.setObject(1,id);
        ResultSet rst=stm.executeQuery();
        if(rst.next()){
            return new Sport(rst.getString(1),rst.getString(2),rst.getString(3));
        }else{
            return null;
        }

    }
    public ArrayList<Sport>getAllSport() throws SQLException, ClassNotFoundException {
        PreparedStatement stm=DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Sports");
        ArrayList<Sport>sports=new ArrayList<>();
        ResultSet rst=stm.executeQuery();
        while (rst.next()){
            sports.add(new Sport(rst.getString(1),rst.getString(2),rst.getString(3)));
        }
        return sports;
    }

    public ArrayList<StudentandSport> getAllStudentandSport() throws SQLException, ClassNotFoundException {
        PreparedStatement stm=DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `Student and" +
                " Sports`");
        ResultSet rst=stm.executeQuery();
        ArrayList<StudentandSport> studentandSports=new ArrayList<>();
        while (rst.next()){
            PreparedStatement stm2=DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Student" +
                    " WHERE StudentIndexNumber=?");
            stm2.setObject(1,rst.getString(2));
            ResultSet rst2=stm2.executeQuery();
            if(rst2.next()){
                studentandSports.add(new StudentandSport(rst.getString(1),rst.getString(2),
                        rst2.getString(2)+" "+rst2.getString(3)));
            }
        }
        return studentandSports;
    }





}
