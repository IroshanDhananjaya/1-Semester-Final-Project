package View.TM;

public class TeacherTM {
    private String teacherID;
    private String teacherName;
    private String address;
    private String gender;
    private String religion;
    private String contact;

    public TeacherTM() {
    }

    public TeacherTM(String teacherID, String teacherName, String address, String gender, String religion,
                    String contact) {
        this.teacherID = teacherID;
        this.teacherName = teacherName;
        this.address = address;
        this.gender = gender;
        this.religion = religion;
        this.contact = contact;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "teacherID='" + teacherID + '\'' +
                ", teacherName='" + teacherName + '\'' +
                ", address='" + address + '\'' +
                ", gender='" + gender + '\'' +
                ", religion='" + religion + '\'' +
                ", contact='" + contact + '\'' +
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }
}
