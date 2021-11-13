package Model;

import java.util.ArrayList;

public class Students {
    private String studentIndexNumber;
    private String studentFirstName;
    private String studentLastName;
    private String gender;
    private String religion;
    private String birthDay;
    private String motherFirstName;
    private String motherLastName;
    private String fatherFirstName;
    private String fartherLastName;
    private String fartherOccupation;
    private String address;
    private String contact;
    private String classID;
    private String adminssionDate;


    public Students() {
    }


    public Students(String studentIndexNumber, String studentFirstName, String studentLastName, String gender, String religion, String birthDay, String motherFirstName, String motherLastName, String fatherFirstName, String fartherLastName, String fartherOccupation, String address, String contact, String classID, String adminssionDate) {
        this.studentIndexNumber = studentIndexNumber;
        this.studentFirstName = studentFirstName;
        this.studentLastName = studentLastName;
        this.gender = gender;
        this.religion = religion;
        this.birthDay = birthDay;
        this.motherFirstName = motherFirstName;
        this.motherLastName = motherLastName;
        this.fatherFirstName = fatherFirstName;
        this.fartherLastName = fartherLastName;
        this.fartherOccupation = fartherOccupation;
        this.address = address;
        this.contact = contact;
        this.classID = classID;
        this.adminssionDate = adminssionDate;
    }

    public Students(String studentIndexNumber, String studentFirstName, String studentLastName, String gender, String religion, String birthDay, String motherFirstName, String motherLastName, String fatherFirstName, String fartherLastName, String fartherOccupation, String address, String contact) {
        this.studentIndexNumber = studentIndexNumber;
        this.studentFirstName = studentFirstName;
        this.studentLastName = studentLastName;
        this.gender = gender;
        this.religion = religion;
        this.birthDay = birthDay;
        this.motherFirstName = motherFirstName;
        this.motherLastName = motherLastName;
        this.fatherFirstName = fatherFirstName;
        this.fartherLastName = fartherLastName;
        this.fartherOccupation = fartherOccupation;
        this.address = address;
        this.contact = contact;
    }

    @Override
    public String toString() {
        return "Students{" +
                "studentIndexNumber='" + studentIndexNumber + '\'' +
                ", studentFirstName='" + studentFirstName + '\'' +
                ", studentLastName='" + studentLastName + '\'' +
                ", gender='" + gender + '\'' +
                ", religion='" + religion + '\'' +
                ", birthDay='" + birthDay + '\'' +
                ", motherFirstName='" + motherFirstName + '\'' +
                ", motherLastName='" + motherLastName + '\'' +
                ", fatherFirstName='" + fatherFirstName + '\'' +
                ", fartherLastName='" + fartherLastName + '\'' +
                ", fartherOccupation='" + fartherOccupation + '\'' +
                ", address='" + address + '\'' +
                ", contact='" + contact + '\'' +
                '}';
    }

    public String getStudentIndexNumber() {
        return studentIndexNumber;
    }

    public void setStudentIndexNumber(String studentIndexNumber) {
        this.studentIndexNumber = studentIndexNumber;
    }

    public String getStudentFirstName() {
        return studentFirstName;
    }

    public void setStudentFirstName(String studentFirstName) {
        this.studentFirstName = studentFirstName;
    }

    public String getStudentLastName() {
        return studentLastName;
    }

    public void setStudentLastName(String studentLastName) {
        this.studentLastName = studentLastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getMotherFirstName() {
        return motherFirstName;
    }

    public void setMotherFirstName(String motherFirstName) {
        this.motherFirstName = motherFirstName;
    }

    public String getMotherLastName() {
        return motherLastName;
    }

    public void setMotherLastName(String motherLastName) {
        this.motherLastName = motherLastName;
    }

    public String getFatherFirstName() {
        return fatherFirstName;
    }

    public void setFatherFirstName(String fatherFirstName) {
        this.fatherFirstName = fatherFirstName;
    }

    public String getFartherLastName() {
        return fartherLastName;
    }

    public void setFartherLastName(String fartherLastName) {
        this.fartherLastName = fartherLastName;
    }

    public String getFartherOccupation() {
        return fartherOccupation;
    }

    public void setFartherOccupation(String fartherOccupation) {
        this.fartherOccupation = fartherOccupation;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }


    public String getClassID() {
        return classID;
    }

    public void setClassID(String classID) {
        this.classID = classID;
    }

    public String getAdminssionDate() {
        return adminssionDate;
    }

    public void setAdminssionDate(String adminssionDate) {
        this.adminssionDate = adminssionDate;
    }
}
