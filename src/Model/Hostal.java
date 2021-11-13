package Model;

public class Hostal {
    private String hostalID;
    private String hostalName;
    private String hostalType;
    private int noOfRoom;
    private String hostalContact;

    public Hostal() {
    }

    public Hostal(String hostalID, String hostalName, String hostalType, int noOfRoom, String hostalContact) {
        this.hostalID = hostalID;
        this.hostalName = hostalName;
        this.hostalType = hostalType;
        this.noOfRoom = noOfRoom;
        this.hostalContact = hostalContact;
    }

    @Override
    public String toString() {
        return "Hostal{" +
                "hostalID='" + hostalID + '\'' +
                ", hostalName='" + hostalName + '\'' +
                ", hostalType='" + hostalType + '\'' +
                ", noOfRoom=" + noOfRoom +
                ", hostalContact='" + hostalContact + '\'' +
                '}';
    }

    public String getHostalID() {
        return hostalID;
    }

    public void setHostalID(String hostalID) {
        this.hostalID = hostalID;
    }

    public String getHostalName() {
        return hostalName;
    }

    public void setHostalName(String hostalName) {
        this.hostalName = hostalName;
    }

    public String getHostalType() {
        return hostalType;
    }

    public void setHostalType(String hostalType) {
        this.hostalType = hostalType;
    }

    public int getNoOfRoom() {
        return noOfRoom;
    }

    public void setNoOfRoom(int noOfRoom) {
        this.noOfRoom = noOfRoom;
    }

    public String getHostalContact() {
        return hostalContact;
    }

    public void setHostalContact(String hostalContact) {
        this.hostalContact = hostalContact;
    }
}
