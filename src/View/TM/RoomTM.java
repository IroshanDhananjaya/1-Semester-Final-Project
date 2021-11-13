package View.TM;

public class RoomTM {
    private String roomID;
    private String hostalID;
    private String details;
    private int noOfBed;

    public RoomTM() {
    }

    public RoomTM(String roomID, String hostalID, String details, int noOfBed) {
        this.roomID = roomID;
        this.hostalID = hostalID;
        this.details = details;
        this.noOfBed = noOfBed;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomID='" + roomID + '\'' +
                ", hostalID='" + hostalID + '\'' +
                ", details='" + details + '\'' +
                ", noOfBed=" + noOfBed +
                '}';
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

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getNoOfBed() {
        return noOfBed;
    }

    public void setNoOfBed(int noOfBed) {
        this.noOfBed = noOfBed;
    }
}
