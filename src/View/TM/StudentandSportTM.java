package View.TM;

public class StudentandSportTM {
    private String sportID;
    private String studentID;
    private String studentName;

    public StudentandSportTM() {
    }

    public StudentandSportTM(String sportID, String studentID) {
        this.sportID = sportID;
        this.studentID = studentID;
    }

    public StudentandSportTM(String sportID, String studentID, String studentName) {
        this.sportID = sportID;
        this.studentID = studentID;
        this.studentName = studentName;
    }

    @Override
    public String toString() {
        return "StudentandSport{" +
                "sportID='" + sportID + '\'' +
                ", studentID='" + studentID + '\'' +
                ", studentName='" + studentName + '\'' +
                '}';
    }

    public String getSportID() {
        return sportID;
    }

    public void setSportID(String sportID) {
        this.sportID = sportID;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
}
