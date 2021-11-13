package View.TM;

public class TeachersClass {
    private String classID;
    private String className;

    public TeachersClass(String classID, String className) {
        this.classID = classID;
        this.className = className;
    }

    public TeachersClass() {
    }

    public String getClassID() {
        return classID;
    }

    public void setClassID(String classID) {
        this.classID = classID;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
