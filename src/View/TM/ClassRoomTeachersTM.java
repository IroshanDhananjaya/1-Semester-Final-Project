package View.TM;

public class ClassRoomTeachersTM {
    private String classID;
    private String teacherID;
    private String  teacherName;

    public ClassRoomTeachersTM() {
    }

    public ClassRoomTeachersTM(String classID, String teacherID, String teacherName) {
        this.classID = classID;
        this.teacherID = teacherID;
        this.teacherName = teacherName;
    }

    @Override
    public String toString() {
        return "ClassRoomTeachersTM{" +
                "classID='" + classID + '\'' +
                ", teacherID='" + teacherID + '\'' +
                ", teacherName='" + teacherName + '\'' +
                '}';
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

    public String getClassID() {
        return classID;
    }

    public void setClassID(String classID) {
        this.classID = classID;
    }
}
