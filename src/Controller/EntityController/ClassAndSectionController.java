package Controller.EntityController;

import DB.DbConnection;
import Model.ClassRoom;
import Model.ClassRoomTeachersDetails;
import Model.Section;
import View.TM.ClassRoomTeachersTM;
import View.TM.StudentDetailsInClassTM;
import View.TM.TeachersClass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClassAndSectionController {
    public List<String> getSectionIDs() throws SQLException, ClassNotFoundException {
        ResultSet rst=
                DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `Section`").executeQuery();
        List<String>ids=new ArrayList<>();
        while (rst.next()){
            ids.add(rst.getString(1));
        }
        return ids;
    }
    public List<String> getClassIDs() throws SQLException, ClassNotFoundException {
        ResultSet rst=
                DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Class").executeQuery();
        List<String>ids=new ArrayList<>();
        while (rst.next()){
            ids.add(rst.getString(1));
        }
        return ids;
    }

    public boolean saveSection(Section section) throws SQLException, ClassNotFoundException {
        PreparedStatement stm;
        Connection con= DbConnection.getInstance().getConnection();
        String duplicateEntry="SELECT 1 FROM `Section` WHERE SectionID=?";
        stm=con.prepareStatement(duplicateEntry);
        stm.setString(1,section.getSectionID());
        ResultSet resultSet = stm.executeQuery();if(!resultSet.next()) {
            String query = "INSERT INTO `Section`VALUES (?,?)";
            stm = con.prepareStatement(query);

            stm.setObject(1, section.getSectionID());
            stm.setObject(2, section.getSectionName());

            return stm.executeUpdate() > 0;
        }else{
            return false;
        }
    }

    public boolean saveClass(ClassRoom classRoom) throws SQLException, ClassNotFoundException {
        Connection con = null;
            PreparedStatement stm;
            con = DbConnection.getInstance().getConnection();
            String duplicateEntry="SELECT 1 FROM Class WHERE ClassID=?";
            stm=con.prepareStatement(duplicateEntry);
            stm.setString(1,classRoom.getClassID());
            ResultSet resultSet = stm.executeQuery();
            if(!resultSet.next()) {
                stm = con.prepareStatement("INSERT INTO Class VALUES (?,?,?,?)");
                stm.setObject(1, classRoom.getClassID());
                stm.setObject(2, classRoom.getClassName());
                stm.setObject(3, classRoom.getSectionID());
                stm.setObject(4, classRoom.getClassDetails());

                return stm.executeUpdate()>0;

            } else {
                return false;
            }
    }

    public boolean updateClass(ClassRoom classRoom) throws SQLException, ClassNotFoundException {
        PreparedStatement stm=DbConnection.getInstance().getConnection().prepareStatement("UPDATE Class SET " +
                "ClassName=?,SectionID=?,ClassDetails=? WHERE ClassID=?");
        stm.setObject(1,classRoom.getClassName());
        stm.setObject(2,classRoom.getSectionID());
        stm.setObject(3,classRoom.getClassDetails());
        stm.setObject(4,classRoom.getClassID());

        return stm.executeUpdate()>0;
    }

    public ClassRoom getClass(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm=DbConnection.getInstance().getConnection().prepareStatement("SELECT * From Class WHERE " +
                "ClassID=?");
        stm.setObject(1,id);
        ResultSet rst=stm.executeQuery();
        if(rst.next()){
            return new ClassRoom(rst.getString(1),rst.getString(2),rst.getString(3),rst.getString(4));
        }else {
            return null;
        }
    }

    public boolean saveClassTeachersDetails(String id, ArrayList<ClassRoomTeachersDetails>details) throws SQLException, ClassNotFoundException {
        for(ClassRoomTeachersDetails temp:details){
            PreparedStatement stm=
                    DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO `Teacher and Class` " +
                            "Values (?,?)");

            stm.setObject(1,temp.getTeacherID());
            stm.setObject(2,id);
            if(stm.executeUpdate()>0){

            }else {
                return false;
            }
        }
        return true;
    }
    public boolean deleteClassRoom(String id) throws SQLException, ClassNotFoundException {
        if(DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM Class WHERE ClassID='"+id+"' ").executeUpdate()>0){
            return true;
        }else{
            return false;
        }
    }
    public ArrayList<ClassRoom> getAllClass() throws SQLException, ClassNotFoundException {
        PreparedStatement statement=DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Class");
        ArrayList<ClassRoom>classRooms=new ArrayList<>();
        ResultSet rst=statement.executeQuery();
        while (rst.next()){
            PreparedStatement statement1=DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM " +
                    "`Section` WHERE SectionId=?");
            statement1.setObject(1,rst.getString(3));
            ResultSet rst2=statement1.executeQuery();
            if(rst2.next()){
                classRooms.add(new ClassRoom(rst.getString(1),rst.getString(2),rst2.getString(2),rst.getString(4)));
            }
        }
        return classRooms;
    }

    public boolean deleteClassFromTeacher(String teacherId,String ClassId) throws SQLException,
            ClassNotFoundException {
        if(DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM `Teacher and Class` WHERE " +
                "TeacherID='"+teacherId+"' AND ClassID='"+ClassId+"'").executeUpdate()>0){
            return true;
        }else{
            return false;
        }
    }

    public boolean deleteClassFromStudent(String studentId,String ClassId) throws SQLException, ClassNotFoundException {
        if(DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM `Student Rejistration` WHERE " +
                "StudentIndexNumber='"+studentId+"' AND ClassID='"+ClassId+"'").executeUpdate()>0){
            return true;
        }else{
            return false;
        }
    }
    public String getClassIDA() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance()
                .getConnection().prepareStatement(
                        "SELECT ClassID FROM Class ORDER BY ClassID DESC LIMIT 1"
                ).executeQuery();
        if (rst.next()){

            int tempId = Integer.
                    parseInt(rst.getString(1).split("-")[1]);
            tempId=tempId+1;

            if (tempId<=9){
                return "Class-00"+tempId;
            }else if(tempId<99){

                return "Class-0"+tempId;

            }else{
                return "Class-"+tempId;
            }

        }else{
            return "Class-001";
        }
    }

    public ArrayList<StudentDetailsInClassTM> getClassStudentDetails(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm=DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `Student " +
                "Rejistration` WHERE ClassID='"+id+"'");
        ArrayList<StudentDetailsInClassTM> detailsInClassTMS=new ArrayList<>();
        ResultSet rst=stm.executeQuery();
        while (rst.next()){
            PreparedStatement stm1=DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Student" +
                    " WHERE StudentIndexNumber=?");
            stm1.setString(1,rst.getString(2));
            ResultSet rst1=stm1.executeQuery();
            if(rst1.next()){
                detailsInClassTMS.add(new StudentDetailsInClassTM(rst.getString(2),
                        rst1.getString(2)+" "+rst1.getString(3),
                        rst.getString(3)));
            }
        }
        return detailsInClassTMS;
    }

    public ArrayList<ClassRoomTeachersTM> getClassTeacher(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm=DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `Teacher and" +
                " Class` WHERE ClassID='"+id+"'");
        ArrayList<ClassRoomTeachersTM> teachersClasses=new ArrayList<>();
        ResultSet rst=stm.executeQuery();
        while (rst.next()){
            PreparedStatement stm1=DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM " +
                    "Teachers WHERE TeacherID=?");
            stm1.setString(1,rst.getString(1));
            ResultSet rst1=stm1.executeQuery();
            if(rst1.next()){
                teachersClasses.add(new ClassRoomTeachersTM(rst.getString(2),rst.getString(1),rst1.getString(2)));
            }
        }
        return teachersClasses;
    }

}
