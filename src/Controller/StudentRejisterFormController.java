package Controller;

import Controller.EntityController.ClassAndSectionController;
import Controller.EntityController.StudentController;
import DB.DbConnection;
import Model.StudentRejisterClassRoom;
import Model.Students;
import Util.AlertMassage;
import Util.ValidationUtil;
import com.jfoenix.controls.JFXDatePicker;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

public class StudentRejisterFormController {
    public TextField txtStudentFirstName;
    public TextField txtStudentLastName;
    public TextField txtStudentIndexNumber;
    public TextField txtMotherFirstName;
    public TextField txtMortherLastName;
    public TextField txtFartherFirstName;
    public TextField txtAddress;
    public TextField txtFartherOccupation;
    public TextField txtFartherLastName;
    public TextField txtContact;
    public ComboBox cmbBirthYear;
    public ComboBox cmbBirthMonth;
    public ComboBox cmbBirthDay;
    public ComboBox cmbGender;
    public ComboBox cmbReligion;
    public Button savebtn;
    public AnchorPane studentRejisterContext;

    public ComboBox cmbClassID;
    public TextField txtAddmisionDate;
    public Label studentIndex;
    public AnchorPane addToClassDate;
    public JFXDatePicker datePicker;
    public Label lblClass;
    public Label lblDate;

    LinkedHashMap<TextField, Pattern> allValidations = new LinkedHashMap<>();
    Pattern idPattern = Pattern.compile("^(S00-)[0-9]{3,4}$");
    Pattern namePattern = Pattern.compile("^[A-z ]{3,20}$");
    Pattern addressPattern = Pattern.compile("^[A-z0-9/, ]{6,30}$");
    Pattern contactPattern = Pattern.compile("^(0)[0-9][-]?[0-9]{8}$");


    public void initialize() {
        setIndexNumber();

        validateInit();
        try {
            loadClassIDs();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        cmbClassID.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
                    try {
                        getFromClassDate((String) newValue);

                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                });
        cmbGender.getItems().addAll("Male", "Female");
        cmbReligion.getItems().addAll("Buddhism", "Muslim", "Tamil");
        cmbBirthYear.getItems().addAll("2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010");
        cmbBirthMonth.getItems().addAll("Jun", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
        cmbBirthDay.getItems().addAll("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16"
                , "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31");

        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy:MM:dd");
            lblDate.setText(LocalDateTime.now().format(formatter1));
        }),new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    public void studentSaveOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
        if(cmbClassID.getSelectionModel().isEmpty()){
            new AlertMassage().errorMassage("Please Select a Class");
            return;
        }
       /* if(datePicker.getValue().){
            new AlertMassage().errorMassage("Please Select a Class & Registration Date");
            return;
        }*/
        String birthDay = cmbBirthYear.getValue() + "-" + cmbBirthMonth.getValue() + "-" + cmbBirthDay.getValue();
        Students s1 = new Students(studentIndex.getText(), txtStudentFirstName.getText(),
                txtStudentLastName.getText(), (String) cmbGender.getValue(), (String) cmbReligion.getValue(), birthDay,
                txtMotherFirstName.getText(), txtMortherLastName.getText(), txtFartherFirstName.getText(),
                txtFartherLastName.getText(), txtFartherOccupation.getText(), txtAddress.getText(),
                txtContact.getText(),(String) cmbClassID.getValue(),lblDate.getText());
        if (new StudentController().saveStudent(s1)) {
            setIndexNumber();
            new AlertMassage().successMassage("Save Successful");
           txtStudentFirstName.clear();txtStudentLastName.clear();cmbBirthDay.getSelectionModel().clearSelection();cmbBirthMonth.getSelectionModel().clearSelection();cmbBirthYear.getSelectionModel().clearSelection();cmbGender.getSelectionModel().clearSelection();cmbReligion.getSelectionModel().clearSelection();txtMortherLastName.clear();txtMotherFirstName.clear();txtFartherFirstName.clear();txtFartherLastName.clear();txtFartherOccupation.clear();txtAddress.clear();txtContact.clear();cmbClassID.getSelectionModel().clearSelection();

        } else {
          new AlertMassage().errorMassage("Student Index Number already added");

        }
    }

    public void textKeyReleased(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(allValidations, savebtn);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                new Alert(Alert.AlertType.INFORMATION, "Aded").showAndWait();
            }
        }
    }


    private void validateInit() {
        savebtn.setDisable(true);
        allValidations.put(txtStudentFirstName, namePattern);
        allValidations.put(txtStudentLastName, namePattern);
        allValidations.put(txtMotherFirstName, namePattern);
        allValidations.put(txtMortherLastName, namePattern);
        allValidations.put(txtFartherFirstName, namePattern);
        allValidations.put(txtFartherLastName, namePattern);
        allValidations.put(txtFartherOccupation, namePattern);
        allValidations.put(txtAddress, addressPattern);
        allValidations.put(txtContact, contactPattern);
    }

    public void cancelOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../View/StudentManageForm.fxml");
        Parent load = FXMLLoader.load(resource);
        studentRejisterContext.getChildren().clear();
        studentRejisterContext.getChildren().add(load);

    }
    public void loadClassIDs() throws SQLException, ClassNotFoundException {
        List<String>classIDs=new ClassAndSectionController().getClassIDs();
        cmbClassID.getItems().addAll(classIDs);
    }

    private void setIndexNumber() {
        try {
            studentIndex.setText(new StudentController().getStudentIndex());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void studentAdmissionPrintOnAction(ActionEvent actionEvent){

        try {
            JasperDesign  Design = JRXmlLoader.load(this.getClass().getResourceAsStream("../View/report/StudentForm" +
                    ".jrxml"));
            JasperReport jasperReport = JasperCompileManager.compileReport(Design);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, new JREmptyDataSource(1));

            JasperViewer.viewReport(jasperPrint,false);

        } catch (JRException e) {
            e.printStackTrace();
        }
    }
    public void getFromClassDate(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Class WHERE ClassID=?");

        stm.setObject(1, id);
        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            lblClass.setText(rst.getString(2));
        }
    }
}
