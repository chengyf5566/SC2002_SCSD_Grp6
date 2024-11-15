package User;
import Utility.PasswordHashing;

public class User{
    private String userID;
    private String password;
    private String role;
    private String name;
    private String gender;

    public User(String userID, String password, String role, String gender, String name) {
        this.userID = userID;
        this.password = password;
        this.role = role;
        this.gender = gender;
        this.name = name;
    }
    

    //getters and setters
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = PasswordHashing.hashPassword(password);
    }

    public String getName() {  
        return name;
    }

    public void setName(String name) {  
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getGender() {  
        return gender;
    }

    public void setGender(String gender) {  
        this.gender = gender;
    }

}
