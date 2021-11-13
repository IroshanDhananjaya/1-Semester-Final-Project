package View.TM;

public class UserTM {
    private String userNIC;
    private String userName;
    private String userPw;
    private String userType;

    public UserTM() {
    }

    public UserTM(String userNIC, String userName, String userPw, String userType) {
        this.userNIC = userNIC;
        this.userName = userName;
        this.userPw = userPw;
        this.userType = userType;
    }

    public String getUserNIC() {
        return userNIC;
    }

    public void setUserNIC(String userNIC) {
        this.userNIC = userNIC;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPw() {
        return userPw;
    }

    public void setUserPw(String userPw) {
        this.userPw = userPw;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
