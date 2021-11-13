package View.TM;

public class SubjectTM {
    private String subjectID;
    private String subjectName;
    private String subjectMedium;

    public SubjectTM(String subjectID, String subjectName, String subjectMedium) {
        this.subjectID = subjectID;
        this.subjectName = subjectName;
        this.subjectMedium = subjectMedium;
    }

    public SubjectTM() {
    }



    @Override
    public String toString() {
        return "Subject{" +
                "subjectID='" + subjectID + '\'' +
                ", subjectName='" + subjectName + '\'' +
                ", subjectMedium='" + subjectMedium + '\'' +
                '}';
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

    public String getSubjectMedium() {
        return subjectMedium;
    }

    public void setSubjectMedium(String subjectMedium) {
        this.subjectMedium = subjectMedium;
    }
}
