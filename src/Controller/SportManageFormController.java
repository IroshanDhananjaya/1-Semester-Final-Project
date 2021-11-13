package Controller;

import Controller.EntityController.SportController;
import Controller.EntityController.StudentController;
import Controller.EntityController.SubjectController;
import DB.DbConnection;
import Model.Sport;
import Model.StudentandSport;
import Model.Students;
import Util.AlertMassage;
import Util.ValidationUtil;
import View.TM.SportTM;
import View.TM.StudentTM;
import View.TM.StudentandSportTM;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import javax.swing.plaf.synth.ColorType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

public class SportManageFormController {
    public AnchorPane addNewSportContext;
    public AnchorPane EditAndDeletContext1;
    public TextField txtAddSportName;
    public TextField txtAddSportID;
    public JFXComboBox cmbAddSportType;
    public JFXTextField txtEditSportName;
    public JFXComboBox cmbEditSportID;
    public JFXComboBox cmbEditSportType;
    public TableView tblSports;
    public TableColumn ColSportID;
    public TableColumn ColSportName;
    public TableColumn ColSportType;
    public JFXComboBox cmbStudentaddIntoSport;
    public JFXComboBox cmbSportaddtoStudent;
    public TableView tblStudentandSport;
    public TableColumn colSportIdwithStudent;
    public TableColumn colStudentId;
    public TableColumn colStudentName;
    public Label lblName;
    public Button btnSave;

    LinkedHashMap<TextField, Pattern> allValidations = new LinkedHashMap<>();
    Pattern idPattern = Pattern.compile("^(Sport-)[0-9]{3,4}$");
    Pattern namePattern = Pattern.compile("^[A-z ]{3,20}$");


    public void initialize(){
        btnSave.setDisable(true);
        validation();

        addNewSportContext.setTranslateX(1000);
        EditAndDeletContext1.setTranslateX(1000);

        ColSportID.setCellValueFactory(new PropertyValueFactory<>("sportID"));
        ColSportName.setCellValueFactory(new PropertyValueFactory<>("sportName"));
        ColSportType.setCellValueFactory(new PropertyValueFactory<>("sportType"));

        colSportIdwithStudent.setCellValueFactory(new PropertyValueFactory<>("sportID"));
        colStudentName.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        colStudentId.setCellValueFactory(new PropertyValueFactory<>("studentID"));


        try {
            loadSportIds();
            loadStudentNames();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            loadAllStudentAndSport(new SportController().getAllStudentandSport());
            loadAllSport(new SportController().getAllSport());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        cmbAddSportType.getItems().addAll("OutDoor","InDoor");
        cmbEditSportType.getItems().addAll("OutDoor","InDoor");

        cmbEditSportID.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
                    try {
                        setEditData(new SportController().getSport((String) newValue));
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                });
        cmbStudentaddIntoSport.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
                    try {
                        getStudentName((String)newValue);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                });

    }

    public void addSportOnAction(ActionEvent actionEvent) {
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.2));
        slide.setNode(addNewSportContext);

        slide.setToX(0);
        slide.play();

        addNewSportContext.setTranslateX(-176);
        slide.setOnFinished((ActionEvent e)-> {
            EditAndDeletContext1.setTranslateX(1000);
        });
    }

    public void EditAndDeletCancel(ActionEvent actionEvent) {
        EditAndDeletContext1.setTranslateX(1000);
    }

    public void editandDeleteOnAction(ActionEvent actionEvent) {
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.2));
        slide.setNode(EditAndDeletContext1);

        slide.setToX(0);
        slide.play();

        EditAndDeletContext1.setTranslateX(-176);
        slide.setOnFinished((ActionEvent e)-> {
            addNewSportContext.setTranslateX(1000);
        });
    }

    public void addnewSportCancel(ActionEvent actionEvent) {
        addNewSportContext.setTranslateX(1000);
    }

    public void AddSportOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Sport sport =new Sport(txtAddSportID.getText(),txtAddSportName.getText(),(String) cmbAddSportType.getValue());
        if(new SportController().saveSport(sport)) {
            loadAllSport(new SportController().getAllSport());
           cmbEditSportID.getItems().addAll(txtAddSportID.getText());
            
          new AlertMassage().successMassage("Save Successful");
        }else {

            new AlertMassage().errorMassage("This Sport ID already added");
        }

    }

    public void studentAddintoSport(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        StudentandSport studentandSport=new StudentandSport((String) cmbSportaddtoStudent.getValue(),
                (String)cmbStudentaddIntoSport.getValue());

        if(new SportController().addStudentIntoSport(studentandSport)) {
            loadAllStudentAndSport(new SportController().getAllStudentandSport());
           new AlertMassage().successMassage("Successful Added");
        }else {
           new AlertMassage().errorMassage("This student already Added in this sport");
        }
    }

    public void SportDeleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if(new SportController().deleteSport((String) cmbEditSportID.getValue())) {
            loadAllSport(new SportController().getAllSport());
           new AlertMassage().successMassage("Delete Successful");
        } else{
           new AlertMassage().errorMassage("Try Again");
        }
    }

    public void SportEditOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Sport sport=new Sport((String) cmbEditSportID.getValue(),txtEditSportName.getText(),
                (String) cmbEditSportType.getValue());

        if(new SportController().updateSport(sport)) {
           new AlertMassage().successMassage("Update Successful");
        }else{
           new AlertMassage().errorMassage("Try Again");
        }

    }

    public void setEditData(Sport sport){
        txtEditSportName.setText(sport.getSportName());
        cmbEditSportType.setValue(sport.getSportType());
    }

    public void loadSportIds() throws SQLException, ClassNotFoundException {
        List<String>sportIds=new SportController().getSportIDs();
        cmbEditSportID.getItems().addAll(sportIds);
        cmbSportaddtoStudent.getItems().addAll(sportIds);
    }
    public void loadAllSport(ArrayList<Sport> sports){
        ObservableList<SportTM> observableList= FXCollections.observableArrayList();
        sports.forEach(e->{observableList.add(new SportTM(e.getSportID(),e.getSportName(),e.getSportType()));});
        tblSports.setItems(observableList);
    }
    private void loadStudentNames() throws SQLException, ClassNotFoundException {
        List<String> StudentID = new StudentController().getStudentIDs();
        cmbStudentaddIntoSport.getItems().addAll(StudentID);
    }

   public String getStudentID(String name) throws SQLException, ClassNotFoundException {
        PreparedStatement stm= DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Student " +
                "WHERE StudentFirstName=?");
        stm.setObject(1,name);
       ResultSet rst=stm.executeQuery();
       if(rst.next()){
           return rst.getString(1);
       }
       return null;
    }
    public void loadAllStudentAndSport(ArrayList<StudentandSport> studentandSports){
        ObservableList<StudentandSportTM> observableList= FXCollections.observableArrayList();
        studentandSports.forEach(e->{observableList.add(new StudentandSportTM(e.getSportID(),e.getStudentID(),e.getStudentName()));});
        tblStudentandSport.setItems(observableList);
    }

    public void getStudentName(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm= DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Student " +
                "WHERE StudentIndexNumber=?");
        stm.setObject(1,id);

        ResultSet rst=stm.executeQuery();
        if(rst.next()){
            lblName.setText(rst.getString(2)+" "+rst.getString(3));
        }
    }

    public void validation(){
        allValidations.put(txtAddSportID,idPattern);
        allValidations.put(txtAddSportName,namePattern);
    }

    public void addSport_KeyReleased(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(allValidations, btnSave);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                new Alert(Alert.AlertType.INFORMATION, "Aded").showAndWait();
            }
        }
    }
}
