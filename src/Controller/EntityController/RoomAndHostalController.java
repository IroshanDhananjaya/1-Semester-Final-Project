package Controller.EntityController;

import DB.DbConnection;
import Model.Hostal;
import Model.Room;
import Model.StudentRoom;
import View.TM.StudentRoomTM;
import javafx.scene.control.ComboBox;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomAndHostalController {

    public List<String> getHostalIDs() throws SQLException, ClassNotFoundException {
        ResultSet rst=
                DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Hostal").executeQuery();
        List<String>ids=new ArrayList<>();
        while (rst.next()){
            ids.add(rst.getString(1));
        }
        return ids;
    }

    public List<String> getRoomIds() throws SQLException, ClassNotFoundException {
        ResultSet rst=DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Room").executeQuery();
        List<String> ids=new ArrayList<>();
        while (rst.next()){
            ids.add(rst.getString(1));
        }
        return ids;
    }


    public boolean saveHostal(Hostal h1) throws SQLException, ClassNotFoundException {
        PreparedStatement stm;
        Connection con= DbConnection.getInstance().getConnection();
        String duplicateEntry="SELECT 1 FROM Hostal WHERE HostalID=?";
        stm=con.prepareStatement(duplicateEntry);
        stm.setString(1,h1.getHostalID());
        ResultSet rst=stm.executeQuery();

        if(!rst.next()) {
            String query = "INSERT INTO Hostal VALUES(?,?,?,?,?)";
            stm = con.prepareStatement(query);

            stm.setObject(1, h1.getHostalID());
            stm.setObject(2, h1.getHostalName());
            stm.setObject(3, h1.getHostalType());
            stm.setObject(4, h1.getNoOfRoom());
            stm.setObject(5, h1.getHostalContact());

            return stm.executeUpdate() > 0;
        }else{
            return false;
        }
    }
    public boolean saveRoom(Room r1) throws SQLException, ClassNotFoundException {
        PreparedStatement stm;
        Connection con= DbConnection.getInstance().getConnection();
        String duplicateEntry="SELECT 1 FROM Room WHERE RoomID=?";
        stm=con.prepareStatement(duplicateEntry);
        stm.setString(1,r1.getRoomID());
        ResultSet resultSet = stm.executeQuery();

        if(!resultSet.next()) {
            String query = "INSERT INTO Room VALUES(?,?,?,?)";
            stm = con.prepareStatement(query);

            stm.setObject(1, r1.getRoomID());
            stm.setObject(2, r1.getHostalID());
            stm.setObject(3, r1.getDetails());
            stm.setObject(4, r1.getNoOfBed());


            return stm.executeUpdate() > 0;
        }else{
            return false;
        }
    }
    public boolean updateRoom(Room room) throws SQLException, ClassNotFoundException {
        PreparedStatement stm=DbConnection.getInstance().getConnection().prepareStatement("UPDATE Room SET " +
                "HostalID=?,RoomDetails=?,NoOfBed=? WHERE RoomID=?");
        stm.setObject(1,room.getHostalID());
        stm.setObject(2,room.getDetails());
        stm.setObject(3,room.getNoOfBed());
        stm.setObject(4,room.getRoomID());

        return stm.executeUpdate()>0;
    }

    public boolean updateHostal(Hostal hostal) throws SQLException, ClassNotFoundException {
        PreparedStatement stm=DbConnection.getInstance().getConnection().prepareStatement("UPDATE Hostal SET " +
                "HostalName=?,HostalType=?,NoOfRoom=?,HostalContact=? WHERE HostalID=?");

        stm.setObject(1,hostal.getHostalName());
        stm.setObject(2,hostal.getHostalType());
        stm.setObject(3,hostal.getNoOfRoom());
        stm.setObject(4,hostal.getHostalContact());
        stm.setObject(5,hostal.getHostalID());

        return stm.executeUpdate()>0;
    }

    public boolean deleteRoom(String id) throws SQLException, ClassNotFoundException {
        if(DbConnection.getInstance().getConnection().prepareStatement("Delete FROM Room WHERE RoomID='"+id+"'").executeUpdate()>0){
            return true;
        }else{
            return false;
        }

    }
    public boolean deleteHostal(String id) throws SQLException, ClassNotFoundException {
        if(DbConnection.getInstance().getConnection().prepareStatement("Delete FROM Hostal WHERE HostalID='"+id+"'").executeUpdate()>0){
            return true;
        }else{
            return false;
        }

    }
    public Room getRoom(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement statement = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM  Room " +
                "WHERE RoomID=?");
        statement.setObject(1,id);
        ResultSet rst=statement.executeQuery();
        if(rst.next()){
            return new Room(rst.getString(1),rst.getString(2),rst.getString(3),rst.getInt(4));
        }
        return null;

    }
    public Hostal getHostal(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement statement = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM  " +
                "Hostal" +
                " " +
                "WHERE HostalID=?");
        statement.setObject(1,id);
        ResultSet rst=statement.executeQuery();
        if(rst.next()){
            return new Hostal(rst.getString(1),rst.getString(2),rst.getString(3),rst.getInt(4),rst.getString(5));
        }
        return null;

    }

    public ArrayList<Room> getRoom() throws SQLException, ClassNotFoundException {
        PreparedStatement stm=DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Room");
        ResultSet rst=stm.executeQuery();
        ArrayList<Room>rooms=new ArrayList<>();
        while (rst.next()){
            rooms.add(new Room(rst.getString(1),rst.getString(2),rst.getString(3),rst.getInt(4)));
        }
        return rooms;
    }

    public boolean addStudentToRoom(StudentRoom room) throws SQLException, ClassNotFoundException {
        PreparedStatement stm1=DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `Student " +
                "Room Details` WHERE StudentIndexNumber=?");
        stm1.setString(1,room.getStudentID());
        ResultSet rst=stm1.executeQuery();
        if(rst.next()){
            return false;
        }

        PreparedStatement stm=DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO `Student Room " +
                "Details` VALUES (?,?,?)");
        stm.setObject(1,room.getRoomID());
        stm.setObject(2,room.getStudentID());
        stm.setObject(3,room.getEntryDate());

         return stm.executeUpdate()>0;
    }
    public ArrayList<StudentRoomTM>getAllStudentandRoomDetails() throws SQLException, ClassNotFoundException {
        PreparedStatement statement=DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM " +
                "`Student Room Details`");
        ArrayList<StudentRoomTM>studentRoomTMS=new ArrayList<>();
        ResultSet rst=statement.executeQuery();
        while (rst.next()){
            PreparedStatement statement1=DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM " +
                    "Student WHERE StudentIndexNumber=?");
            statement1.setObject(1,rst.getString(2));
            ResultSet rst1=statement1.executeQuery();
            if(rst1.next()){
                PreparedStatement statement2=DbConnection.getInstance().getConnection().prepareStatement("SELECT * " +
                        "FROM Room WHERE RoomID=?");
                statement2.setObject(1,rst.getString(1));
                ResultSet rst2=statement2.executeQuery();
                if(rst2.next()){
                    studentRoomTMS.add(new StudentRoomTM(rst.getString(2),rst1.getString(2)+" "+rst1.getString(3),
                            rst.getString(1),rst2.getString(2),rst.getString(3)));
                }
            }
        }
        return studentRoomTMS;
    }

    public boolean deleteStudentsRoom(String id) throws SQLException, ClassNotFoundException {
        if(DbConnection.getInstance().getConnection().prepareStatement("Delete FROM `Student Room Details` WHERE " +
                "StudentIndexNumber='"+id+"'").executeUpdate()>0){
            return true;
        }else{
            return false;
        }

    }
}
