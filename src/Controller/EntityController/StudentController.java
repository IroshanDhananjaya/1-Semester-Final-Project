package Controller.EntityController;

import DB.DbConnection;
import Model.Students;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentController {
    public List<String> getStudentIDs() throws SQLException, ClassNotFoundException {
        ResultSet rst=
                DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Student").executeQuery();
        List<String>ids=new ArrayList<>();
        while (rst.next()){
            ids.add(rst.getString(1));
        }
        return ids;
    }
    public List<String> getStudentNames() throws SQLException, ClassNotFoundException {
        ResultSet rst=
                DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Student").executeQuery();
        List<String>Names=new ArrayList<>();
        while (rst.next()){
            Names.add(rst.getString(2));
        }
        return Names;
    }

   /*public boolean saveStudent(Students s1) throws SQLException, ClassNotFoundException {
       PreparedStatement stm;
       Connection con = DbConnection.getInstance().getConnection();
       String studentDuplicateId="SELECT 1 FROM Student WHERE StudentIndexNumber=?";
       stm=con.prepareStatement(studentDuplicateId);
       stm.setString(1,s1.getStudentIndexNumber());
       ResultSet rst=stm.executeQuery();

       if(!rst.next()) {
           String query = "INSERT INTO Student VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
           stm = con.prepareStatement(query);

           stm.setObject(1, s1.getStudentIndexNumber());
           stm.setObject(2, s1.getStudentFirstName());
           stm.setObject(3, s1.getStudentLastName());
           stm.setObject(4, s1.getGender());
           stm.setObject(5, s1.getReligion());
           stm.setObject(6, s1.getBirthDay());
           stm.setObject(7, s1.getMotherFirstName());
           stm.setObject(8, s1.getMotherLastName());
           stm.setObject(9, s1.getFatherFirstName());
           stm.setObject(10, s1.getFartherLastName());
           stm.setObject(11, s1.getFartherOccupation());
           stm.setObject(12, s1.getAddress());
           stm.setObject(13, s1.getContact());
           return stm.executeUpdate() > 0;
       }else {
           return false;
       }


   }
   */
   public boolean saveStudent(Students s1){
        Connection con=null;
        PreparedStatement stm;

       try {
           con=DbConnection.getInstance().getConnection();
           String studentDuplicateId="SELECT 1 FROM Student WHERE StudentIndexNumber=?";
           stm=con.prepareStatement(studentDuplicateId);
           stm.setString(1,s1.getStudentIndexNumber());
           ResultSet rst=stm.executeQuery();

           if(!rst.next()){
               con.setAutoCommit(false);
               String query = "INSERT INTO Student VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
               stm = con.prepareStatement(query);

               stm.setObject(1, s1.getStudentIndexNumber());
               stm.setObject(2, s1.getStudentFirstName());
               stm.setObject(3, s1.getStudentLastName());
               stm.setObject(4, s1.getGender());
               stm.setObject(5, s1.getReligion());
               stm.setObject(6, s1.getBirthDay());
               stm.setObject(7, s1.getMotherFirstName());
               stm.setObject(8, s1.getMotherLastName());
               stm.setObject(9, s1.getFatherFirstName());
               stm.setObject(10, s1.getFartherLastName());
               stm.setObject(11, s1.getFartherOccupation());
               stm.setObject(12, s1.getAddress());
               stm.setObject(13, s1.getContact());
               if(stm.executeUpdate()>0){
                   if(addStudentToClassRoom(s1.getStudentIndexNumber(),s1.getClassID(),s1.getAdminssionDate())){
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

   public boolean addStudentToClassRoom(String studentID,String classID,String addmisionDate) throws SQLException,
           ClassNotFoundException {
       Connection con= DbConnection.getInstance().getConnection();
       String query="INSERT INTO `Student Rejistration` VALUES(?,?,?)";
       PreparedStatement stm =con.prepareStatement(query);
       stm.setObject(1,classID);
       stm.setObject(2,studentID);
       stm.setObject(3,addmisionDate);

       return stm.executeUpdate()>0;
   }

   public boolean updateStudent(Students s) throws SQLException, ClassNotFoundException {
        PreparedStatement stm=DbConnection.getInstance().getConnection().prepareStatement("UPDATE Student SET " +
                "StudentFirstName=?,StudentLastName=?,Gender=?,Religion=?,BirthDay=?,MotherFirstName=?," +
                "MotherLastName=?,FatherFirstName=?,FatherLastName=?,FartherOccupation=?,StudentAddress=?,Contact=? " +
                "WHERE StudentIndexNumber=?");

        stm.setObject(1,s.getStudentFirstName());
        stm.setObject(2,s.getStudentLastName());
        stm.setObject(3,s.getGender());
        stm.setObject(4,s.getReligion());
        stm.setObject(5,s.getBirthDay());
        stm.setObject(6,s.getMotherFirstName());
        stm.setObject(7,s.getMotherLastName());
        stm.setObject(8,s.getFatherFirstName());
        stm.setObject(9,s.getFartherLastName());
        stm.setObject(10,s.getFartherOccupation());
        stm.setObject(11,s.getAddress());
        stm.setObject(12,s.getContact());
        stm.setObject(13,s.getStudentIndexNumber());

        return stm.executeUpdate()>0;

   }

   public boolean deleteStudent(String id) throws SQLException, ClassNotFoundException {
        if(DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM Student WHERE " +
                "StudentIndexNumber='"+id+"'").executeUpdate()>0){
            return true;
        }else {
            return false;
        }
   }

    public Students getStudent(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance()
                .getConnection().prepareStatement("SELECT * FROM Student WHERE StudentIndexNumber=?");
        stm.setObject(1, id);

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {

            return new Students(

                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7),
                    rst.getString(8),
                    rst.getString(9),
                    rst.getString(10),
                    rst.getString(11),
                    rst.getString(12),
                    rst.getString(13)
            );
        } else {
            return null;
        }
    }

   public ArrayList<Students> getAllStudent() throws SQLException, ClassNotFoundException {
        PreparedStatement stm=
                DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM  Student");
        ResultSet rst=stm.executeQuery();
        ArrayList<Students>studentsts=new ArrayList<>();
        while (rst.next()){
            studentsts.add(new Students(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7),
                    rst.getString(8),
                    rst.getString(9),
                    rst.getString(10),
                    rst.getString(11),
                    rst.getString(12),
                    rst.getString(13)
            ));
        }
        return studentsts;
   }

    public String getStudentIndex() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance()
                .getConnection().prepareStatement(
                        "SELECT StudentIndexNumber FROM Student ORDER BY StudentIndexNumber DESC LIMIT 1"
                ).executeQuery();
        if (rst.next()){

            int tempId = Integer.
                    parseInt(rst.getString(1).split("-")[1]);
            tempId=tempId+1;
            System.out.println(tempId+1);
            if (tempId<=9){
                return "S00-00"+tempId;
            }else if(tempId<99){

                return "S00-0"+tempId;

            }else{
                return "S00-"+tempId;
            }

        }else{
            return "S00-001";
        }
    }
}
