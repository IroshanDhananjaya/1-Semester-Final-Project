package View.TM;

public class StudentDetailsInClassTM {
    private String studentIndex;
    private String studentName;
    private String date;

    public StudentDetailsInClassTM(String StudentIndex, String teacherName, String date) {
        this.studentIndex = StudentIndex;
        this.studentName = teacherName;
        this.date = date;
    }

    public StudentDetailsInClassTM() {
    }

    public String getStudentIndex() {
        return studentIndex;
    }

    public void setStudentIndex(String studentIndex) {
        this.studentIndex = studentIndex;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
