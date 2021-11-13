package View.TM;

public class ClassRoomTM {
    private String classID;
    private String className;
    private String section;
    private String classDetails;

    public ClassRoomTM() {
    }

    public ClassRoomTM(String classID, String className, String section, String classDetails) {
        this.classID = classID;
        this.className = className;
        this.section = section;
        this.classDetails = classDetails;
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

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getClassDetails() {
        return classDetails;
    }

    public void setClassDetails(String classDetails) {
        this.classDetails = classDetails;
    }
}
