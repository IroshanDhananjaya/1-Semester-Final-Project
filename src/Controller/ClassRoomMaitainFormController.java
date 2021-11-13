package Controller;

import Controller.EntityController.ClassAndSectionController;
import Model.ClassRoom;
import Model.Section;
import Util.AlertMassage;
import Util.ValidationUtil;
import com.jfoenix.controls.JFXComboBox;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

public class ClassRoomMaitainFormController {
    public AnchorPane addSectionContext;
    public JFXComboBox cmbSectionID;
    public JFXComboBox cmbTeacherId;
    public TableView tblTeachers;
    public TableColumn colTeachers;
    public TextField txtSectionName;
    public TextField txtSectionID;
    public Label lblAlreadyAdded;
    public Button btnSectionSave;
    public Button btnClassSave;
    public TextField txtClassID;
    public TextField txtClassName;
    public TextField txtDetails;
    public TableColumn colClassID;
    public TableColumn colTeacherID;
    public AnchorPane ClassManazgeContext;
    public Label lblClassID;

    LinkedHashMap<TextField, Pattern> allValidations = new LinkedHashMap<>();
    LinkedHashMap<TextField, Pattern> allValidations1 = new LinkedHashMap<>();
    Pattern ClassIDPattern = Pattern.compile("^(CR-)[0-9]{3,4}$");
    Pattern SectionIDPattern = Pattern.compile("^(Sec-)[0-9]{3,4}$");
    Pattern namePattern = Pattern.compile("^[A-z0-9/ ]{1,}$");
    Pattern detailsPattern = Pattern.compile("^[A-z ]{5,25}$");


    public void initialize() {
        setClassID();

        validateInit();
        validateInitSection();


        addSectionContext.setTranslateX(1000);
        try {

            loadSectionIds();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void SectionAddContextOnAction(ActionEvent actionEvent) {
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.2));
        slide.setNode(addSectionContext);

        slide.setToX(0);
        slide.play();

        addSectionContext.setTranslateX(-176);
        slide.setOnFinished((ActionEvent e) -> {
        });
    }


    public void addSectionOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Section section = new Section(txtSectionID.getText(), txtSectionName.getText());

        if (new ClassAndSectionController().saveSection(section)) {
            cmbSectionID.getItems().addAll(section.getSectionID());
            new AlertMassage().successMassage("Save Successful");
        } else {
            new AlertMassage().errorMassage("Section ID already added");
        }
    }

    public void saveClassOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        ClassRoom classRoom = new ClassRoom(lblClassID.getText(), txtClassName.getText(), (String) cmbSectionID.getValue(),
                txtDetails.getText());
        if (new ClassAndSectionController().saveClass(classRoom)) {
            setClassID();
            new AlertMassage().successMassage("Save Successful");

        } else {
            new AlertMassage().errorMassage("Class ID Already Added");
        }


    }

    public void addCancelSectionOnAction(ActionEvent actionEvent) {
        addSectionContext.setTranslateX(1000);
    }


    private void loadSectionIds() throws SQLException, ClassNotFoundException {
        List<String> SectionIDs = new ClassAndSectionController().getSectionIDs();
        cmbSectionID.getItems().addAll(SectionIDs);

    }


    public void textfield_keyReleased(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(allValidations, btnClassSave);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                new Alert(Alert.AlertType.INFORMATION, "Aded").showAndWait();
            }
        }
    }

    public void Sectiontextfield_keyReleased(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(allValidations1, btnSectionSave);
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
        btnClassSave.setDisable(true);
        allValidations.put(txtClassName, namePattern);
        allValidations.put(txtDetails, detailsPattern);
    }

    private void validateInitSection() {
        btnSectionSave.setDisable(true);
        allValidations1.put(txtSectionID, SectionIDPattern);
        allValidations1.put(txtSectionName, namePattern);
    }


    public void cancelOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../View/SectionClassroomManageForm.fxml");
        Parent load = FXMLLoader.load(resource);
        ClassManazgeContext.getChildren().clear();
        ClassManazgeContext.getChildren().add(load);
    }

    public void setClassID() {
        try {
            lblClassID.setText(new ClassAndSectionController().getClassIDA());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
