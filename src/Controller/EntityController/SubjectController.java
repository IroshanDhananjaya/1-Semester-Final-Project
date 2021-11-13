package Controller.EntityController;

import DB.DbConnection;
import Model.Subject;
import Model.SubjectandTeacher;
import View.TM.AllTeachersSubjectTM;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubjectController {

    public List<String> getSubjectId() throws SQLException, ClassNotFoundException {
        ResultSet rst= DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Subject").executeQuery();
        List<String>ids=new ArrayList<>();
        while(rst.next()){
            ids.add(rst.getString(1));
        }
        return ids;
    }

    public List<String> getSubjectName() throws SQLException, ClassNotFoundException {
        ResultSet rst= DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Subject").executeQuery();
        List<String>names=new ArrayList<>();
        while(rst.next()){
            names.add(rst.getString(2));
        }
        return names;
    }

    public boolean saveSubject(Subject subject) throws SQLException, ClassNotFoundException {
        PreparedStatement stm;
        Connection con=DbConnection.getInstance().getConnection();
        String duplicateEntray="SELECT 1 FROM Subject WHERE SubjectID=?";
        stm=con.prepareStatement(duplicateEntray);
        stm.setString(1,subject.getSubjectID());
        ResultSet resultSet = stm.executeQuery();

        if(!resultSet.next()) {
            String query = ("INSERT INTO Subject VALUES (?,?,?)");
            stm=con.prepareStatement(query);
            stm.setObject(1, subject.getSubjectID());
            stm.setObject(2, subject.getSubjectName());
            stm.setObject(3, subject.getSubjectMedium());

            return stm.executeUpdate() > 0;
        }else{
            return false;
        }
    }

    public boolean deleteSubject(String id) throws SQLException, ClassNotFoundException {
        if(DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM Subject WHERE SubjectID='"+id+"'").executeUpdate()>0){
            return true;
        }else{
            return false;
        }

    }

    public boolean updateSubject(Subject subject) throws SQLException, ClassNotFoundException {
        PreparedStatement stm=DbConnection.getInstance().getConnection().prepareStatement("UPDATE Subject SET " +
                "SubjectName=?,SubjectMeadium=? WHERE SubjectID=?");

        stm.setObject(1,subject.getSubjectName());
        stm.setObject(2,subject.getSubjectMedium());
        stm.setObject(3,subject.getSubjectID());

        return stm.executeUpdate()>0;

    }

    public Subject getSubject(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm=DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Subject " +
                "WHERE SubjectID=?");
        stm.setObject(1,id);
        ResultSet rst=stm.executeQuery();
        if(rst.next()){
            return new Subject(rst.getString(1),rst.getString(2),rst.getString(3));
        }else {
            return null;
        }
    }

    public ArrayList<Subject> getAllSubject() throws SQLException, ClassNotFoundException {
        PreparedStatement stm=DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Subject");
        ResultSet rst=stm.executeQuery();
        ArrayList<Subject> subjects=new ArrayList<>();
        while (rst.next()){
            subjects.add(new Subject(rst.getString(1),rst.getString(2),rst.getString(3)));
        }
        return subjects;
    }

    public boolean addSubjectToTeacher(SubjectandTeacher subjectandTeacher) throws SQLException, ClassNotFoundException {
        PreparedStatement stm1=DbConnection.getInstance().getConnection().prepareStatement("SELECT * From `Teacher " +
                "and Subject` WHERE TeacherID=? AND SubjectID=?");
        stm1.setString(1,subjectandTeacher.getTeacherID());
        stm1.setString(2,subjectandTeacher.getSubjectID());
        ResultSet rst=stm1.executeQuery();
        if(rst.next()){
            return false;
        }

        PreparedStatement stm=DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO `Teacher and " +
                "Subject` VALUES (?,?)");

        stm.setObject(1,subjectandTeacher.getTeacherID());
        stm.setObject(2,subjectandTeacher.getSubjectID());
        return stm.executeUpdate()>0;
    }

    public ArrayList<AllTeachersSubjectTM> getAllTeachersandSubject() throws SQLException, ClassNotFoundException {
        PreparedStatement stm=DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `Teacher and" +
                " Subject`");
        ArrayList<AllTeachersSubjectTM>allTeachersSubjectTMS=new ArrayList<>();
        ResultSet rst=stm.executeQuery();
        while(rst.next()){
            PreparedStatement stm2=DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM " +
                    "Teachers WHERE TeacherID=?");
            stm2.setObject(1,rst.getString(1));
            ResultSet rst2=stm2.executeQuery();
            if(rst2.next()) {
                 PreparedStatement stm3 = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM " +
                         "Subject" +
                        " " +
                        "WHERE SubjectID=?");
                stm3.setObject(1, rst.getString(2));
                ResultSet rst3 = stm3.executeQuery();
                if(rst3.next()) {
                    allTeachersSubjectTMS.add(new AllTeachersSubjectTM(rst.getString(1), rst2.getString(2), rst.getString(2),
                            rst3.getString(2), rst3.getString(3)));
                }
            }
        }
        return allTeachersSubjectTMS;
    }

    public boolean deleteSubjectFromTeacher(String teacherId,String SubjectId) throws SQLException, ClassNotFoundException {
        if(DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM `Teacher and Subject` WHERE " +
                "TeacherID='"+teacherId+"' AND SubjectID='"+SubjectId+"'").executeUpdate()>0){
            return true;
        }else{
            return false;
        }
    }
}
