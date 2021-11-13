package View.TM;

public class TeacherSubjectTM {
    private String subjectID;
    private String subjectName;

    public TeacherSubjectTM(String subjectID, String subjectName) {
        this.subjectID = subjectID;
        this.subjectName = subjectName;
    }

    public TeacherSubjectTM() {
    }

    public String getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(String subjectID) {
        this.subjectID = subjectID;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
}
