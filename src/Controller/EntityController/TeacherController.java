package Controller.EntityController;

import DB.DbConnection;
import Model.Students;
import Model.Teacher;
import View.TM.TeacherSubjectTM;
import View.TM.TeachersClass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeacherController {

    public List<String> getTeacherIDs() throws SQLException, ClassNotFoundException {
        ResultSet rst=
                DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Teachers").executeQuery();
        List<String>ids=new ArrayList<>();
        while (rst.next()){
            ids.add(rst.getString(1));
        }
        return ids;
    }
    public List<String> getTeacherNames() throws SQLException, ClassNotFoundException {
        ResultSet rst=
                DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Teachers").executeQuery();
        List<String>names=new ArrayList<>();
        while (rst.next()){
            names.add(rst.getString(2));
        }
        return names;
    }
/*
    public boolean saveTeacher(Teacher t1) throws SQLException, ClassNotFoundException {
        PreparedStatement stm;
        Connection con=null;
        con= DbConnection.getInstance().getConnection();
        String teacherDuplicateEntry="SELECT 1 FROM Teachers WHERE TeacherID=?";
        stm=con.prepareStatement(teacherDuplicateEntry);
        stm.setString(1,t1.getTeacherID());
        ResultSet rst=stm.executeQuery();

        if(!rst.next()) {
            String query = "INSERT INTO Teachers VALUES (?,?,?,?,?,?)";
            stm = con.prepareStatement(query);


            stm.setObject(1, t1.getTeacherID());
            stm.setObject(2, t1.getTeacherName());
            stm.setObject(3, t1.getAddress());
            stm.setObject(4, t1.getGender());
            stm.setObject(5, t1.getReligion());
            stm.setObject(6, t1.getContact());

            return stm.executeUpdate() > 0;
        }else {
            return false;
        }
    }*/

    public boolean saveTeacher(Teacher t1){
        Connection con=null;
        PreparedStatement stm;

        try {
            con=DbConnection.getInstance().getConnection();
            String studentDuplicateId="SELECT 1 FROM Teachers WHERE TeacherID=?";
            stm=con.prepareStatement(studentDuplicateId);
            stm.setString(1,t1.getTeacherID());
            ResultSet rst=stm.executeQuery();
            if(!rst.next()){
                con.setAutoCommit(false);
                String query = "INSERT INTO Teachers VALUES (?,?,?,?,?,?)";
                stm = con.prepareStatement(query);
                stm.setObject(1, t1.getTeacherID());
                stm.setObject(2, t1.getTeacherName());
                stm.setObject(3, t1.getAddress());
                stm.setObject(4, t1.getGender());
                stm.setObject(5, t1.getReligion());
                stm.setObject(6, t1.getContact());
                if(stm.executeUpdate()>0){
                    if(addSubjectTeacher(t1.getTeacherID(),t1.getDetails())){
                        con.commit();
                        return true;
                    }else{
                        con.rollback();
                        return false;
                    }
                }else {
                    con.rollback();
                    return false;
                }
            }else{
                return false;
            }


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return false;

    }

    public boolean addSubjectTeacher(String teacherID,ArrayList<TeacherSubjectTM>details) throws SQLException, ClassNotFoundException {
        for(TeacherSubjectTM temp:details){
            PreparedStatement stm=DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO `Teacher " +
                    "and Subject` VALUES (?,?)");
            stm.setString(1,teacherID);
            stm.setString(2,temp.getSubjectID());

            if(stm.executeUpdate()>0){

            }else{
                return false;
            }
        }
        return true;
    }

    public boolean updateTeacher(Teacher teacher) throws SQLException, ClassNotFoundException {
        PreparedStatement stm=DbConnection.getInstance().getConnection().prepareStatement("UPDATE Teachers SET " +
                "TeacherName=?,TeacherAddress=?,Gender=?,Religion=?,Contact=? WHERE TeacherID=?");

        stm.setObject(1,teacher.getTeacherName());
        stm.setObject(2,teacher.getAddress());
        stm.setObject(3,teacher.getGender());
        stm.setObject(4,teacher.getReligion());
        stm.setObject(5,teacher.getContact());
        stm.setObject(6,teacher.getTeacherID());

        return stm.executeUpdate()>0;
    }

    public boolean deleteTeacher(String id) throws SQLException, ClassNotFoundException {
        if(DbConnection.getInstance().getConnection().prepareStatement("Delete FROM Teachers WHERE TeacherID='"+id+"'").executeUpdate()>0){
            return true;
        }else{
            return false;
        }
    }
    public Teacher getTeacher(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm=DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Teachers " +
                "WHERE TeacherID=?");
        stm.setObject(1,id);
        ResultSet rst=stm.executeQuery();
        if(rst.next()){
            return new Teacher(rst.getString(1),rst.getString(2),rst.getString(3),rst.getString(4),rst.getString(5),
                    rst.getString(6));
        }
        return null;
    }

    public ArrayList<Teacher> getAllTeacher() throws SQLException, ClassNotFoundException {
        PreparedStatement statement=DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM " +
                "Teachers");
        ResultSet set=statement.executeQuery();
        ArrayList<Teacher>teachers=new ArrayList<>();
        while (set.next()){
            teachers.add(new Teacher(set.getString(1),set.getString(2),set.getString(3),set.getString(4),
                    set.getString(5),set.getString(6)));
        }
        return teachers;
    }

    public ArrayList<TeachersClass> getTeacherClasses(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm=DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `Teacher and" +
                " Class` WHERE TeacherID=?");
        stm.setObject(1,id);
        ArrayList<TeachersClass>teachersClasses=new ArrayList<>();
        ResultSet rst=stm.executeQuery();
        while (rst.next()){
            PreparedStatement stm2=DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Class " +
                    "WHERE ClassID=?");
            stm2.setObject(1,rst.getString(2));
            ResultSet rst2=stm2.executeQuery();

            if(rst2.next()){
                teachersClasses.add(new TeachersClass(rst.getString(2),rst2.getString(2)));
            }
        }
        return teachersClasses;
    }

    public ArrayList<TeacherSubjectTM> getTeacherSubject(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm=DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `Teacher and" +
                " Subject` WHERE TeacherID=?");
        ArrayList<TeacherSubjectTM>teacherSubjectTMS=new ArrayList<>();
        stm.setObject(1,id);
        ResultSet rst=stm.executeQuery();
        while (rst.next()){
            PreparedStatement stm2=DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Subject" +
                    " WHERE SubjectID=?");
            stm2.setObject(1,rst.getString(2));
            ResultSet rst2=stm2.executeQuery();
            if(rst2.next()){
                teacherSubjectTMS.add(new TeacherSubjectTM(rst.getString(2),rst2.getString(2)));
            }
        }
        return teacherSubjectTMS;
    }
    public String getTeacherID() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance()
                .getConnection().prepareStatement(
                        "SELECT TeacherID FROM Teachers ORDER BY TeacherID DESC LIMIT 1"
                ).executeQuery();
        if (rst.next()){

            int tempId = Integer.
                    parseInt(rst.getString(1).split("-")[1]);
            tempId=tempId+1;

            if (tempId<=9){
                return "T00-00"+tempId;
            }else if(tempId<99){

                return "T00-0"+tempId;

            }else{
                return "T00-"+tempId;
            }

        }else{
            return "T00-001";
        }
    }


}
