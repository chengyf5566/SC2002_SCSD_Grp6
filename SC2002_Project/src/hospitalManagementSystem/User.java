package hospitalManagementSystem;

public class User{
    private String userID;
    private String password;
    private String role;
    private String name;
    private String gender;

    public User(String userID, String password, String role) {
        this.userID = userID;
        this.password = password;
        this.role = role;
    }

    public String getUserId() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {  // Add this method
        return name;
    }

    public void setName(String name) {  // Add this setter for name
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getGender() {  // Add this method
        return gender;
    }

    public void setGender(String gender) {  // Add this setter for name
        this.gender = gender;
    }

}
