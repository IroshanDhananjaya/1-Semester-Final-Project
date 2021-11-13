package Model;

public class ClassRoomTeachersDetails {
    private String classID;
    private String teacherID;

    public ClassRoomTeachersDetails(String classID, String teacherID) {
        this.classID = classID;
        this.teacherID = teacherID;
    }

    public ClassRoomTeachersDetails() {
    }

    @Override
    public String toString() {
        return "ClassRoomTeachersDetails{" +
                "classID='" + classID + '\'' +
                ", teacherID='" + teacherID + '\'' +
                '}';
    }

    public String getClassID() {
        return classID;
    }

    public void setClassID(String classID) {
        this.classID = classID;
    }

    public String getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(String teacherID) {
        this.teacherID = teacherID;
    }
}
