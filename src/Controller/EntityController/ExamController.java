package Controller.EntityController;

import DB.DbConnection;
import Model.Exam;
import Model.StudentExamDetails;
import View.TM.ExamDetailsTM;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExamController {

    public List<String>getExamIds() throws SQLException, ClassNotFoundException {
        PreparedStatement stm=DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Exam");
        List<String> examids=new ArrayList<>();
        ResultSet rst=stm.executeQuery();
        while (rst.next()){
            examids.add(rst.getString(1));
        }
        return examids;
    }

    public boolean saveExam(Exam exam) throws SQLException, ClassNotFoundException {
        PreparedStatement stm;
        Connection con= DbConnection.getInstance().getConnection();
        String duplicateEntry="SELECT 1 FROM Exam WHERE ExamNumber=?";
        stm=con.prepareStatement(duplicateEntry);
        stm.setString(1,exam.getExamNumber());
        ResultSet resultSet = stm.executeQuery();

        if(!resultSet.next()){
            String query="INSERT INTO Exam VALUES (?,?)";
            stm=con.prepareStatement(query);

            stm.setString(1,exam.getExamNumber());
            stm.setString(2,exam.getExamName());

            return stm.executeUpdate()>0;
        }else{
            return false;
        }
    }

    public boolean saveStudentExamDetails(StudentExamDetails examDetails) throws SQLException, ClassNotFoundException {
        PreparedStatement stm;
        Connection con=DbConnection.getInstance().getConnection();
        String duplicate="SELECT * FROM `Student Exam Details` WHERE StudentIndexNumber=? AND ExamNumber=?";
        stm=con.prepareStatement(duplicate);
        stm.setString(1,examDetails.getStudentIndexNumber());
        stm.setString(2,examDetails.getExamNumber());

        ResultSet rst=stm.executeQuery();
        if(!rst.next()){
            String query="INSERT INTO `Student Exam Details` VALUES (?,?,?,?,?)";
            stm=con.prepareStatement(query);

            stm.setString(1,examDetails.getStudentIndexNumber());
            stm.setString(2,examDetails.getExamNumber());
            stm.setString(3,examDetails.getMarks());
            stm.setString(4,examDetails.getResultType());
            stm.setString(5,examDetails.getHeldDate());

            return stm.executeUpdate()>0;
        }else{
            return false;
        }
    }



    public boolean updateExam(Exam exam) throws SQLException, ClassNotFoundException {
        PreparedStatement stm=DbConnection.getInstance().getConnection().prepareStatement("UPDATE Exam SET " +
                "ExamName=?" +
                " WHERE ExamNumber=?");
         stm.setString(1,exam.getExamName());
         stm.setString(2,exam.getExamNumber());

         return stm.executeUpdate()>0;
    }

    public boolean deleteExam(String Id) throws SQLException, ClassNotFoundException {
        if(DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM Exam WHERE ExamNumber='"+Id+"'").executeUpdate()>0){
            return true;
        }else {
            return false;
        }

    }

    public boolean updateStudentExamDetails(StudentExamDetails examDetails) throws SQLException, ClassNotFoundException {
        PreparedStatement stm=DbConnection.getInstance().getConnection().prepareStatement("UPDATE `Student Exam " +
                "Details`SET Marks=?,ResultType=? WHERE StudentIndexNumber=? AND ExamNumber=?");
        stm.setString(1,examDetails.getMarks());
        stm.setString(2,examDetails.getResultType());
        stm.setString(3,examDetails.getStudentIndexNumber());
        stm.setString(4,examDetails.getExamNumber());

       return stm.executeUpdate()>0;
    }

    public boolean deleteStudentandExamDetails(String sId,String eID) throws SQLException, ClassNotFoundException {
        if(DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM `Student Exam Details` WHERE " +
                "StudentIndexNumber='"+sId+"' AND ExamNumber='"+eID+"' ").executeUpdate()>0){
            return true;
        }else {
            return false;
        }
    }

    public ArrayList<Exam> getAllExam() throws SQLException, ClassNotFoundException {
        PreparedStatement stm=DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Exam");
        ArrayList<Exam> exams=new ArrayList<>();
        ResultSet rst=stm.executeQuery();
        while (rst.next()){
            exams.add(new Exam(rst.getString(1),rst.getString(2)));
        }
        return exams;
    }

    public ArrayList<ExamDetailsTM> getStudentAllExamDetails(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm=DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `Student " +
                "Exam Details` WHERE StudentIndexNumber=? ");
        stm.setString(1,id);
        ArrayList<ExamDetailsTM> examDetailsTMS=new ArrayList<>();
        ResultSet rst=stm.executeQuery();
        while (rst.next()){
            PreparedStatement stm1=DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Exam " +
                    "WHERE ExamNumber=?");
            stm1.setString(1,rst.getString(2));
            ResultSet rst1=stm1.executeQuery();
            if(rst1.next()){
                examDetailsTMS.add(new ExamDetailsTM(rst.getString(2),rst1.getString(2),rst.getString(3),
                        rst.getString(4),rst.getString(5)));
            }
        }
        return examDetailsTMS;
    }

}
