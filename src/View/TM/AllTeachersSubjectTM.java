package View.TM;

public class AllTeachersSubjectTM {
    private String teacherID;
    private String teacherName;
    private String sunjectID;
    private String subjectName;
    private String subjectMedium;

    public AllTeachersSubjectTM() {
    }

    public AllTeachersSubjectTM(String teacherID, String teacherName, String sunjectID, String subjectName, String subjectMedium) {
        this.teacherID = teacherID;
        this.teacherName = teacherName;
        this.sunjectID = sunjectID;
        this.subjectName = subjectName;
        this.subjectMedium = subjectMedium;
    }

    public String getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(String teacherID) {
        this.teacherID = teacherID;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getSunjectID() {
        return sunjectID;
    }

    public void setSunjectID(String sunjectID) {
        this.sunjectID = sunjectID;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectMedium() {
        return subjectMedium;
    }

    public void setSubjectMedium(String subjectMedium) {
        this.subjectMedium = subjectMedium;
    }
}
