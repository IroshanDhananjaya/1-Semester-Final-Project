package Model;

import java.util.ArrayList;
import java.util.List;

public class ClassRoom {
    private String classID;
    private String className;
    private String sectionID;
    private String classDetails;
    private ArrayList<ClassRoomTeachersDetails> details;

    public ClassRoom() {
    }

    public ClassRoom(String classID, String className, String sectionID, String classDetails, ArrayList<ClassRoomTeachersDetails> details) {
        this.classID = classID;
        this.className = className;
        this.sectionID = sectionID;
        this.classDetails = classDetails;
        this.details = details;
    }
    public ClassRoom(String classID, String className, String sectionID, String classDetails) {
        this.classID = classID;
        this.className = className;
        this.sectionID = sectionID;
        this.classDetails = classDetails;

    }

    @Override
    public String toString() {
        return "ClassRoom{" +
                "classID='" + classID + '\'' +
                ", className='" + className + '\'' +
                ", sectionID='" + sectionID + '\'' +
                ", classDetails='" + classDetails + '\'' +
                ", details=" + details +
                '}';
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

    public String getSectionID() {
        return sectionID;
    }

    public void setSectionID(String sectionID) {
        this.sectionID = sectionID;
    }

    public String getClassDetails() {
        return classDetails;
    }

    public void setClassDetails(String classDetails) {
        this.classDetails = classDetails;
    }

    public ArrayList<ClassRoomTeachersDetails> getDetails() {
        return details;
    }

    public void setDetails(ArrayList<ClassRoomTeachersDetails> details) {
        this.details = details;
    }
}
