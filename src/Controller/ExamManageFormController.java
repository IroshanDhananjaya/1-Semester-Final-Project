package Controller;

import Controller.EntityController.*;
import DB.DbConnection;
import Model.Exam;
import Model.StudentExamDetails;
import Util.AlertMassage;
import View.TM.ExamTM;
import View.TM.TeachersClass;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExamManageFormController {
    public AnchorPane examManageContext;
    public JFXComboBox cmbStudents;

    public JFXTextField txtMarks;
    public JFXComboBox cmbResultType;
    public JFXDatePicker dateHeld;
    public TableColumn colExamName;
    public TableColumn colExamNumber;
    public TableView <ExamTM>tblExamList;
    public JFXTextField txtExamNumber;
    public JFXTextField txtExamName;
    public JFXComboBox cmbSelectExams;
    public Label lblStudent;
    public Label lblExam;

    public void initialize(){

        cmbResultType.getItems().addAll("A","B","C","S","F");

        try {
            loadIds();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        colExamNumber.setCellValueFactory(new PropertyValueFactory<>("examNumber"));
        colExamName.setCellValueFactory(new PropertyValueFactory<>("examName"));

        try {
            loadAllExams(new ExamController().getAllExam());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        cmbStudents.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
                    try {
                        getStudentName((String) newValue);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                });
        cmbSelectExams.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
                    try {
                        getExamName((String)newValue);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                });
    }

    public void markAddOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        StudentExamDetails examDetails=new StudentExamDetails((String) cmbStudents.getValue(),
                (String) cmbSelectExams.getValue(),txtMarks.getText(),(String)cmbResultType.getValue(),
                dateHeld.getValue().toString());

        if(new ExamController().saveStudentExamDetails(examDetails)){
            new AlertMassage().successMassage("Mark Add Successful");
        }else{
            new AlertMassage().errorMassage("Already Added");
        }
    }

    public void cancelOnAction(ActionEvent actionEvent) {
    }

    public void deleteExamOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if(new ExamController().deleteExam(txtExamNumber.getText())){
            new AlertMassage().successMassage("Delete Successful");
            txtExamName.clear();
            txtExamNumber.clear();
        } else {
            new AlertMassage().errorMassage("Please Select the row where the wants to Delete");

        }
    }

    public void updateExamOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Exam exam=new Exam(txtExamNumber.getText(),txtExamName.getText());

            if (new ExamController().updateExam(exam)) {

                new AlertMassage().successMassage("Update Successful");
                tblExamList.refresh();
                txtExamName.clear();
                txtExamNumber.clear();
            } else {
                new AlertMassage().errorMassage("Please Select the row where the wants to Update");
            }

    }

    public void addExamOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Exam exam=new Exam(txtExamNumber.getText(),txtExamName.getText());

        if(new ExamController().saveExam(exam)){
            loadAllExams(new ExamController().getAllExam());
            new AlertMassage().successMassage("Save Successful");
            txtExamName.clear();
            txtExamNumber.clear();
        }else{
            new AlertMassage().errorMassage("Exam Number Already Added");
        }
    }

    public void loadAllExams(ArrayList<Exam> exams){
        ObservableList<ExamTM> observableList= FXCollections.observableArrayList();
        exams.forEach(e->{observableList.addAll(new ExamTM(e.getExamNumber(),e.getExamName()));});
        tblExamList.setItems(observableList);
    }
    public void loadIds() throws SQLException, ClassNotFoundException {
        List<String>examIds=new ExamController().getExamIds();
        cmbSelectExams.getItems().addAll(examIds);

        List<String>studentIds=new StudentController().getStudentIDs();
        cmbStudents.getItems().addAll(studentIds);
    }

    public void getStudentName(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm= DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Student " +
                "WHERE StudentIndexNumber=?");
        stm.setObject(1,id);

        ResultSet rst=stm.executeQuery();
        if(rst.next()){
            lblStudent.setText(rst.getString(2)+" "+rst.getString(3));
        }
    }
    public void getExamName(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm= DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Exam WHERE " +
                "ExamNumber=?");
        stm.setObject(1,id);

        ResultSet rst=stm.executeQuery();
        if(rst.next()){
            lblExam.setText(rst.getString(2));
        }
    }

    public void TableClicked(MouseEvent mouseEvent) {
        if(tblExamList.getSelectionModel().getSelectedItem()!=null){
            ExamTM exam= (ExamTM) tblExamList.getSelectionModel().getSelectedItem();
                txtExamNumber.setText(exam.getExamNumber());
                txtExamName.setText(exam.getExamName());
        }else{
            new AlertMassage().errorMassage("Please Select the row where the wants to remove");
        }
    }
}
