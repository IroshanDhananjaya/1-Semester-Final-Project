package Controller;

import Controller.EntityController.SubjectController;
import Controller.EntityController.TeacherController;
import DB.DbConnection;
import Model.Teacher;
import Util.AlertMassage;
import Util.ValidationUtil;
import View.TM.TeacherSubjectTM;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

public class AddNewTeacherFormController {
    public TextField txtTeacherFirstName;
    public TextField txtTeacherLastName;
    public TextField txtTeacherID;
    public JFXComboBox cmbGender;
    public JFXComboBox cmbReligion;
    public TextField txtAddress;
    public TextField txtContact;
    public Button saveBtn;
    public AnchorPane addNewTeacheContext;
    public Label lblTeacherID;
    public JFXComboBox cmbSubjectID;
    public Label lblSubjectName;
    public Label lblAlreadyAdded;
    public TableView tblTeacherSubject;
    public TableColumn colSubject;


    LinkedHashMap<TextField, Pattern> allValidations = new LinkedHashMap<>();

    Pattern namePattern = Pattern.compile("^[A-z ]{3,20}$");
    Pattern addressPattern = Pattern.compile("^[A-z0-9/, ]{6,30}$");
    Pattern contactPattern = Pattern.compile("^(0)[0-9][-]?[0-9]{8}$");


    public void initialize(){
        lblAlreadyAdded.setVisible(false);
        colSubject.setCellValueFactory(new PropertyValueFactory<>("subjectName"));

        try {
            loadSubjectId();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        setTeacherId();
        validateUnit();
        cmbGender.getItems().addAll("Male","Female");
        cmbReligion.getItems().addAll("Buddhism","Muslim","Tamil");

        cmbSubjectID.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
                    try {
                       getSubjectName((String)newValue);
                        lblAlreadyAdded.setVisible(false);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                });
    }

    public void teacherSaveOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
        if(obList.isEmpty()){
            new AlertMassage().errorMassage("Please Select subject");
            return;
        }

        ArrayList<TeacherSubjectTM> details=new ArrayList<>();
        for(TeacherSubjectTM temp:obList){
            details.add(new TeacherSubjectTM(temp.getSubjectID(),temp.getSubjectName()));
        }

        String name=txtTeacherFirstName.getText()+" "+txtTeacherLastName.getText();

        Teacher t1=new Teacher(lblTeacherID.getText(),name,txtAddress.getText(),(String) cmbGender.getValue(),
                (String)cmbReligion.getValue(),txtContact.getText(),details);

        if(new TeacherController().saveTeacher(t1)) {
            setTeacherId();
            new AlertMassage().successMassage("Save Successful");
           txtTeacherFirstName.clear();txtTeacherLastName.clear();txtAddress.clear();txtContact.clear();cmbSubjectID.getSelectionModel().clearSelection();cmbGender.getSelectionModel().clearSelection();cmbReligion.getSelectionModel().clearSelection();tblTeacherSubject.getItems().clear();
        } else{
           new AlertMassage().errorMassage("This Teacher ID already added");
        }


    }

    public void setTeacherId(){
        try {
            lblTeacherID.setText(new TeacherController().getTeacherID());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void validateUnit(){
        saveBtn.setDisable(true);
        allValidations.put(txtTeacherFirstName,namePattern);
        allValidations.put(txtTeacherLastName,namePattern);
        allValidations.put(txtAddress,addressPattern);
        allValidations.put(txtContact,contactPattern);
    }


    public void textField_key_Released(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(allValidations,saveBtn);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                new Alert(Alert.AlertType.INFORMATION, "Aded").showAndWait();
            }
        }
    }

    public void cancelOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../View/TeacherManageForm.fxml");
        Parent load = FXMLLoader.load(resource);
        addNewTeacheContext.getChildren().clear();
        addNewTeacheContext.getChildren().add(load);
    }

    ObservableList<TeacherSubjectTM>obList= FXCollections.observableArrayList();
    public void addSubjectForTeacher(ActionEvent actionEvent) {
        TeacherSubjectTM subjectTM=new TeacherSubjectTM((String) cmbSubjectID.getValue(),lblSubjectName.getText());
        for (TeacherSubjectTM temp:obList) {
            if(cmbSubjectID.getValue().equals(temp.getSubjectID())) {
                lblAlreadyAdded.setVisible(true);
                return;
            }
        }

        obList.add(subjectTM);
        tblTeacherSubject.setItems(obList);

    }

    public void loadSubjectId() throws SQLException, ClassNotFoundException {
        List<String> subIds=new SubjectController().getSubjectId();
        cmbSubjectID.getItems().addAll(subIds);
    }

    public void getSubjectName(String  id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm= DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Subject " +
                "WHERE SubjectID=?");
        stm.setString(1,id);

        ResultSet rst=stm.executeQuery();
        if(rst.next()){
            lblSubjectName.setText(rst.getString(2));
        }
    }
}
