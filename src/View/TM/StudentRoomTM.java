package View.TM;

public class StudentRoomTM {
    private String studentIndex;
    private String studentName;
    private String roomID;
    private String hostalID;
    private String entryDate;

    public StudentRoomTM(String studentIndex, String studentName, String roomID, String hostalID, String entryDate) {
        this.studentIndex = studentIndex;
        this.studentName = studentName;
        this.roomID = roomID;
        this.hostalID = hostalID;
        this.entryDate = entryDate;
    }

    public StudentRoomTM() {
    }

    public String getStudentIndex() {
        return studentIndex;
    }

    public void setStudentIndex(String studentIndex) {
        this.studentIndex = studentIndex;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public String getHostalID() {
        return hostalID;
    }

    public void setHostalID(String hostalID) {
        this.hostalID = hostalID;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }
}
