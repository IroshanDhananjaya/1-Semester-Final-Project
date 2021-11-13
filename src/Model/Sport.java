package Model;

public class Sport {
    private String sportID;
    private String sportName;
    private String sportType;

    public Sport(String sportID, String sportName, String sportType) {
        this.sportID = sportID;
        this.sportName = sportName;
        this.sportType = sportType;
    }

    public Sport() {
    }

    @Override
    public String toString() {
        return "Sport{" +
                "sportID='" + sportID + '\'' +
                ", sportName='" + sportName + '\'' +
                ", sportType='" + sportType + '\'' +
                '}';
    }

    public String getSportID() {
        return sportID;
    }

    public void setSportID(String sportID) {
        this.sportID = sportID;
    }

    public String getSportName() {
        return sportName;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName;
    }

    public String getSportType() {
        return sportType;
    }

    public void setSportType(String sportType) {
        this.sportType = sportType;
    }
}
