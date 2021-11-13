package Model;

public class StudentRoom {

    private String roomID;
    private String studentID;
    private String entryDate;

    public StudentRoom(String roomID, String studentID, String entryDate) {
        this.roomID = roomID;
        this.studentID = studentID;
        this.entryDate = entryDate; }

    public StudentRoom() {
    }


    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }
}
