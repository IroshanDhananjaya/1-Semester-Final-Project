package View.TM;

public class ExamDetailsTM {
    private String examNumber;
    private String examName;
    private String mark;
    private String result;
    private String heldDate;

    public ExamDetailsTM(String examNumber, String examName, String mark, String result, String heldDate) {
        this.examNumber = examNumber;
        this.examName = examName;
        this.mark = mark;
        this.result = result;
        this.heldDate = heldDate;
    }

    public ExamDetailsTM() {
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

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getHeldDate() {
        return heldDate;
    }

    public void setHeldDate(String heldDate) {
        this.heldDate = heldDate;
    }
}
