package Controller;

import DB.DbConnection;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class OtherUserDashBoardFormController {
    public AnchorPane DashboardFullScreenContext;
    public AnchorPane DashboardMainWindowContext;
    public Label lblStudent;
    public Label lblTeacher;
    public Label lblClass;
    public Label lblSport;
    public PieChart PieChartStudent;
    public Label lblTime;
    public Label lblDate;
    public Label lbluserName;
    public PieChart PieChartTeacher;
    public Label lbluserName1;
    int male;
    int female;
    int Tmale;
    int Tfemale;

    public void initialize(){
        lbluserName.setText(LoginPageFormController.name);
        lbluserName1.setText(LoginPageFormController.name);




        //pieChart();
        try {
            studentCount();
            TeacherCount();
            SportCount();
            ClassCount();
            pieChart();
          
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        {
            Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy:MM:dd");
                lblTime.setText(LocalDateTime.now().format(formatter));
                lblDate.setText(LocalDateTime.now().format(formatter1));
            }),new KeyFrame(Duration.seconds(1)));
            clock.setCycleCount(Animation.INDEFINITE);
            clock.play();
        }
    }

    public void StudentManageOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../View/StudentManageForm.fxml");
        Parent load = FXMLLoader.load(resource);
        DashboardMainWindowContext.getChildren().clear();
        DashboardMainWindowContext.getChildren().add(load);
    }

    public void TeacherManageOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../View/TeacherManageForm.fxml");
        Parent load = FXMLLoader.load(resource);
        DashboardMainWindowContext.getChildren().clear();
        DashboardMainWindowContext.getChildren().add(load);

    }

    public void SectionManageOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../View/SectionClassroomManageForm.fxml");
        Parent load = FXMLLoader.load(resource);
        DashboardMainWindowContext.getChildren().clear();
        DashboardMainWindowContext.getChildren().add(load);
    }

    public void SubjectManageOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../View/SubjectManageForm.fxml");
        Parent load = FXMLLoader.load(resource);
        DashboardMainWindowContext.getChildren().clear();
        DashboardMainWindowContext.getChildren().add(load);
    }

    public void HostalManageOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../View/HostalManageForm.fxml");
        Parent load = FXMLLoader.load(resource);
        DashboardMainWindowContext.getChildren().clear();
        DashboardMainWindowContext.getChildren().add(load);
    }

    public void SportManageOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../View/SportManageForm.fxml");
        Parent load = FXMLLoader.load(resource);
        DashboardMainWindowContext.getChildren().clear();
        DashboardMainWindowContext.getChildren().add(load);
    }

    public void homeOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../View/OtherUserDashBoardForm.fxml");
        Parent load = FXMLLoader.load(resource);
        DashboardFullScreenContext.getChildren().clear();
        DashboardFullScreenContext.getChildren().add(load);
    }
    public void examOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../View/ExamManageForm.fxml");
        Parent load = FXMLLoader.load(resource);
        DashboardMainWindowContext.getChildren().clear();
        DashboardMainWindowContext.getChildren().add(load);
    }

    public void studentCount() throws SQLException, ClassNotFoundException {
        PreparedStatement stm= DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Student");
        ResultSet rst=stm.executeQuery();
        int a=0;
        while (rst.next()){
            a++;
            lblStudent.setText(String.valueOf(a));
            if(rst.getString(4).equals("Male")){
                male++;
            }else{female++;}
        }
    }
    public void TeacherCount() throws SQLException, ClassNotFoundException {
        PreparedStatement stm= DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Teachers");
        ResultSet rst=stm.executeQuery();
        int a=0;
        while (rst.next()){
            a++;
            lblTeacher.setText(String.valueOf(a));
            if(rst.getString(4).equals("Male")){
                Tmale++;
            }else{Tfemale++;}
        }
    }
    public void ClassCount() throws SQLException, ClassNotFoundException {
        PreparedStatement stm= DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Class");
        ResultSet rst=stm.executeQuery();
        int a=0;
        while (rst.next()){
            a++;
            lblClass.setText(String.valueOf(a));

        }
    }
    public void SportCount() throws SQLException, ClassNotFoundException {
        PreparedStatement stm= DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Sports");
        ResultSet rst=stm.executeQuery();
        int a=0;
        while (rst.next()){
            a++;
            lblSport.setText(String.valueOf(a));
        }
    }
    public void pieChart(){
        ObservableList<PieChart.Data> pieChartData= FXCollections.observableArrayList(
                new PieChart.Data("Female Students",female),
                new PieChart.Data("Male Students",male),
                new PieChart.Data("Female Teachers",Tfemale),
                new PieChart.Data("Male Teachers",Tmale)
             /* new PieChart.Data("Student",Integer.parseInt(lblStudent.getText())),
              new PieChart.Data("Teacher",Integer.parseInt(lblTeacher.getText())),
              new PieChart.Data("Sports",Integer.parseInt(lblSport.getText())),
              new PieChart.Data("Class",Integer.parseInt(lblClass.getText()))*/
        );

        pieChartData.forEach(data -> {data.nameProperty().bind(Bindings.concat(data.getName()," ",
                data.pieValueProperty()));});
        PieChartStudent.getData().addAll(pieChartData);
    }


    public void logOutOnAction(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to log out", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();

        if(buttonType.get().equals(ButtonType.YES)){
            URL resource = getClass().getResource("../View/LoginPageForm.fxml");
            Parent load = FXMLLoader.load(resource);
            DashboardFullScreenContext.getChildren().clear();
            DashboardFullScreenContext.getChildren().add(load);
        }
    }

    public void printStudentAddmisionOnAction(ActionEvent actionEvent) {
        try {
            JasperDesign Design = JRXmlLoader.load(this.getClass().getResourceAsStream("../View/report/StudentForm" +
                    ".jrxml"));
            JasperReport jasperReport = JasperCompileManager.compileReport(Design);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, new JREmptyDataSource(1));

            JasperViewer.viewReport(jasperPrint,false);

        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    public void printTeacherRejisterOnAction(ActionEvent actionEvent) {
        try {
            JasperDesign Design = JRXmlLoader.load(this.getClass().getResourceAsStream("../View/report/TeacherRejister.jrxml"));
            JasperReport jasperReport = JasperCompileManager.compileReport(Design);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, new JREmptyDataSource(1));

            JasperViewer.viewReport(jasperPrint,false);

        } catch (JRException e) {
            e.printStackTrace();
        }
    }
}
