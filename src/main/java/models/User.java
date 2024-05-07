package models;

public class User {
    private int userID;
    private String password;
    private String email;
    private String name;
    private int status;

    public User(int userID, String password, String email, String name, int status) {
        this.userID = userID;
        this.password = password;
        this.email = email;
        this.name = name;
        this.status = status;
    }
    public User(String password, String email, String name) {
        this.password = password;
        this.email = email;
        this.name = name;
    }
    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
