package hospitalManagementSystem;

public abstract class Alluser {
    private String name;
    private String userID;
    private String password;
    private char gender;

    // Constructor
    public Alluser(String name, String userID, String password, char gender) {
        this.name = name;
        this.userID = userID;
        this.password = password;
        this.gender = gender;
    }

    // Getter for name
    public String getName() {
        return name;
    }

    // Setter for name
    public void setName(String name) {
        this.name = name;
    }

    // Getter for userID
    public String getUserID() {
        return userID;
    }

    // Setter for userID
    public void setUserID(String userID) {
        this.userID = userID;
    }

    // Getter for password
    public String getPassword() {
        return password;
    }

    // Setter for password
    public void setPassword(String password) {
        this.password = password;
    }

    // Getter for gender
    public char getGender() {
        return gender;
    }

    // Setter for gender
    public void setGender(char gender) {
        this.gender = gender;
    }
}
