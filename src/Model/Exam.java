package Model;

public class Exam {
    private String examNumber;
    private String examName;

    public Exam(String examNumber, String examName) {
        this.examNumber = examNumber;
        this.examName = examName;
    }

    public Exam() {
    }

    public String getExamNumber() {
        return examNumber;
    }

    public void setExamNumber(String examNumber) {
        this.examNumber = examNumber;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }
}
