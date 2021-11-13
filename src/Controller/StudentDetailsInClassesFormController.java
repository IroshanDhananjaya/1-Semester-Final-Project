package Controller;

import Controller.EntityController.ClassAndSectionController;
import DB.DbConnection;
import View.TM.ClassRoomTeachersTM;
import View.TM.StudentDetailsInClassTM;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import net.sf.jasperreports.engine.JRException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;



public class StudentDetailsInClassesFormController {
    public TableView tblStudent;
    public TableColumn colIndexNumber;
    public TableColumn colSName;
    public TableColumn colDate;
    public TableView tblTeacher;
    public TableColumn colID;
    public TableColumn colTName;
    public JFXComboBox cmbClass;
    public Label lblClassName;


    public void initialize() {
        colIndexNumber.setCellValueFactory(new PropertyValueFactory<>("studentIndex"));
        colSName.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        colID.setCellValueFactory(new PropertyValueFactory<>("teacherID"));
        colTName.setCellValueFactory(new PropertyValueFactory<>("teacherName"));

        try {
            loadClassID();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        cmbClass.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
                    try {
                        loadAllStudent(new ClassAndSectionController().getClassStudentDetails((String) newValue));
                        loadAllTeacher(new ClassAndSectionController().getClassTeacher((String) newValue));
                        getToClassDate((String) newValue);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                });

    }

    public void loadAllStudent(ArrayList<StudentDetailsInClassTM> detailsInClassTMS) {
        ObservableList<StudentDetailsInClassTM> observableList = FXCollections.observableArrayList();
        detailsInClassTMS.forEach(e -> {
            observableList.addAll(new StudentDetailsInClassTM(e.getStudentIndex(),
                    e.getStudentName(), e.getDate()));
        });
        tblStudent.setItems(observableList);

    }

    public void loadAllTeacher(ArrayList<ClassRoomTeachersTM> teachersTMS) {
        ObservableList<ClassRoomTeachersTM> observableList = FXCollections.observableArrayList();
        teachersTMS.forEach(e -> {
            observableList.add(new ClassRoomTeachersTM(e.getClassID(), e.getTeacherID(),
                    e.getTeacherName()));
        });
        tblTeacher.setItems(observableList);
    }

    public void loadClassID() throws SQLException, ClassNotFoundException {
        List<String> classIDs = new ClassAndSectionController().getClassIDs();
        cmbClass.getItems().addAll(classIDs);
    }

    public void getToClassDate(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Class WHERE ClassID=?");

        stm.setObject(1, id);
        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            lblClassName.setText(rst.getString(2));
        }

    }

    public void printClassDetailsOnAction(ActionEvent actionEvent) throws JRException {

        try {
            /*Create a report using table data*/
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/View/report/ClassDetails" +
                    ".jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(design);
            /*Get all values from table*/
            ObservableList<StudentDetailsInClassTM> items = tblStudent.getItems();
            /*Create a Bean Array Data Source and pass the table values to it*/
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, null, new JRBeanArrayDataSource(items.toArray()));
            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }
}
