package Model;

public class SubjectandTeacher {
    private String teacherID;
    private String subjectID;

    public SubjectandTeacher(String teacherID, String subjectID) {
        this.teacherID = teacherID;
        this.subjectID = subjectID;
    }

    public SubjectandTeacher() {
    }

    public String getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(String teacherID) {
        this.teacherID = teacherID;
    }

    public String getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(String subjectID) {
        this.subjectID = subjectID;
    }
}
