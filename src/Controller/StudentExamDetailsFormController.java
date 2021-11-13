package Controller;

import Controller.EntityController.ExamController;
import Controller.EntityController.StudentController;
import DB.DbConnection;
import Model.StudentExamDetails;
import Util.AlertMassage;
import View.TM.ExamDetailsTM;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentExamDetailsFormController {
    public JFXComboBox cmbStudent;
    public Label lblStudentName;
    public TableView<ExamDetailsTM> tblExamDetails;
    public TableColumn colExamNumber;
    public TableColumn colExamName;
    public TableColumn colExamMark;
    public TableColumn colExamResult;
    public TableColumn colHeldDate;
    public JFXTextField txtExamMark;
    public JFXComboBox cmbExamResultType;
    public JFXTextField txtExamNumber;
    public JFXTextField txtExamName;
    public JFXTextField txthelDate;

    public void initialize() {
        cmbExamResultType.getItems().addAll("A", "B", "C", "S", "F");

        colExamNumber.setCellValueFactory(new PropertyValueFactory<>("examNumber"));
        colExamName.setCellValueFactory(new PropertyValueFactory<>("examName"));
        colExamMark.setCellValueFactory(new PropertyValueFactory<>("mark"));
        colExamResult.setCellValueFactory(new PropertyValueFactory<>("result"));
        colHeldDate.setCellValueFactory(new PropertyValueFactory<>("heldDate"));

        try {
            loadStudent();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        cmbStudent.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
                    try {
                        loadTable(new ExamController().getStudentAllExamDetails((String) newValue));
                        getStudentName((String)newValue);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                });
    }

    public void editOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        StudentExamDetails examDetails = new StudentExamDetails((String) cmbStudent.getValue(), txtExamNumber.getText(),
                txtExamMark.getText(), (String) cmbExamResultType.getValue(), txthelDate.getText());

        if (new ExamController().updateStudentExamDetails(examDetails)) {
            loadTable(new ExamController().getStudentAllExamDetails((String) cmbStudent.getValue()));
            new AlertMassage().successMassage("Update Successful");
        } else {
            new AlertMassage().errorMassage("Please Select the row where the wants to Update");
        }
    }

    public void deleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (new ExamController().deleteStudentandExamDetails((String) cmbStudent.getValue(), txtExamNumber.getText())) {
            loadTable(new ExamController().getStudentAllExamDetails((String) cmbStudent.getValue()));
            new AlertMassage().successMassage("Delete Successful");

        } else {
            new AlertMassage().errorMassage("Please Select the row where the wants to Delete");

        }
    }

    public void cancelOnAction(ActionEvent actionEvent) {
    }


    public void loadTable(ArrayList<ExamDetailsTM> examDetailsTMS) {
        ObservableList<ExamDetailsTM> observableList = FXCollections.observableArrayList();
        examDetailsTMS.forEach(e -> {
            observableList.addAll(new ExamDetailsTM(e.getExamNumber(), e.getExamName(),
                    e.getMark(), e.getResult(), e.getHeldDate()));
        });
        tblExamDetails.setItems(observableList);
    }

    public void loadStudent() throws SQLException, ClassNotFoundException {
        List<String> studentIds = new StudentController().getStudentIDs();
        cmbStudent.getItems().addAll(studentIds);
    }

    public void tblExamDetailsClicked(MouseEvent mouseEvent) {
        if (tblExamDetails.getSelectionModel().getSelectedItem() != null) {
            ExamDetailsTM examDetailsTM = tblExamDetails.getSelectionModel().getSelectedItem();
            txtExamNumber.setText(examDetailsTM.getExamNumber());
            txtExamName.setText(examDetailsTM.getExamName());
            txtExamMark.setText(examDetailsTM.getMark());
            cmbExamResultType.setValue(examDetailsTM.getResult());
            txthelDate.setText(examDetailsTM.getHeldDate());
        } else {
            new AlertMassage().errorMassage("Please Select the row where the wants to remove");
        }
    }
    public void getStudentName(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm= DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Student " +
                "WHERE StudentIndexNumber=?");
        stm.setObject(1,id);

        ResultSet rst=stm.executeQuery();
        if(rst.next()){
            lblStudentName.setText(rst.getString(2)+" "+rst.getString(3));
        }
    }
}
