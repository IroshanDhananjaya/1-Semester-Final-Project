package View.TM;

public class SportTM {
    private String sportID;
    private String sportName;
    private String sportType;

    public SportTM(String sportID, String sportName, String sportType) {
        this.sportID = sportID;
        this.sportName = sportName;
        this.sportType = sportType;
    }

    public SportTM() {
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
