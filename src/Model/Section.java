package Model;

public class Section {
    private String sectionID;
    private String sectionName;

    public Section() {
    }

    public Section(String sectionID, String sectionName) {
        this.sectionID = sectionID;
        this.sectionName = sectionName;
    }

    @Override
    public String toString() {
        return "Section{" +
                "sectionID='" + sectionID + '\'' +
                ", sectionName='" + sectionName + '\'' +
                '}';
    }

    public String getSectionID() {
        return sectionID;
    }

    public void setSectionID(String sectionID) {
        this.sectionID = sectionID;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }
}
