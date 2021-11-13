package Model;

public class StudentExamDetails {
    private String StudentIndexNumber;
    private String examNumber;
    private String marks;
    private String resultType;
    private String heldDate;

    public StudentExamDetails(String studentIndexNumber, String examNumber, String marks, String resultType, String heldDate) {
        StudentIndexNumber = studentIndexNumber;
        this.examNumber = examNumber;
        this.marks = marks;
        this.resultType = resultType;
        this.heldDate = heldDate;
    }

    public StudentExamDetails() {
    }

    public String getStudentIndexNumber() {
        return StudentIndexNumber;
    }

    public void setStudentIndexNumber(String studentIndexNumber) {
        StudentIndexNumber = studentIndexNumber;
    }

    public String getExamNumber() {
        return examNumber;
    }

    public void setExamNumber(String examNumber) {
        this.examNumber = examNumber;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public String getHeldDate() {
        return heldDate;
    }

    public void setHeldDate(String heldDate) {
        this.heldDate = heldDate;
    }
}
