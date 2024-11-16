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
    

    //Getters
    public String getUserID() {
        return userID;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {  
        return name;
    }

    public String getRole() {
        return role;
    }

    public String getGender() {  
        return gender;
    }
    
    
    //Setters
    public void setUserID(String userID) {
        this.userID = userID;
    }
    
    public void setPassword(String password) {
        this.password = PasswordHashing.hashPassword(password);
    }
    
    public void setName(String name) {  
        this.name = name;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
    
    public void setGender(String gender) {  
        this.gender = gender;
    }

    
    
    

}
