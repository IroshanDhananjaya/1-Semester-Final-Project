package Model;

public class StudentRejisterClassRoom {

    private String classID;
    private String StudentIndexNumber;
    private String rejisterDate;

    public StudentRejisterClassRoom() {
    }

    public StudentRejisterClassRoom(String classID, String studentIndexNumber, String rejisterDate) {

        this.classID = classID;
        StudentIndexNumber = studentIndexNumber;
        this.rejisterDate = rejisterDate;
    }

    @Override
    public String toString() {
        return "StudentRejisterClassRoom{" +
                ", classID='" + classID + '\'' +
                ", StudentIndexNumber='" + StudentIndexNumber + '\'' +
                ", rejisterDate='" + rejisterDate + '\'' +
                '}';
    }


    public String getClassID() {
        return classID;
    }

    public void setClassID(String classID) {
        this.classID = classID;
    }

    public String getStudentIndexNumber() {
        return StudentIndexNumber;
    }

    public void setStudentIndexNumber(String studentIndexNumber) {
        StudentIndexNumber = studentIndexNumber;
    }

    public String getRejisterDate() {
        return rejisterDate;
    }

    public void setRejisterDate(String rejisterDate) {
        this.rejisterDate = rejisterDate;
    }
}
