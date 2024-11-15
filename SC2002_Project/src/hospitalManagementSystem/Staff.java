package hospitalManagementSystem;

public class Staff extends User {
    private int age; // Only the age field is specific to Staff

    public Staff(String userID, String password, String role, String gender, String name, int age) {
        super(userID, password, role, gender, name); // Delegate other fields to the User constructor
        this.age = age; // Initialize the age field
    }

    // Getter and Setter for age
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    // Getter and Setter for userID
    public String getUserID() {
        return super.getUserID();
    }

    public void setUserID(String userID) {
        super.setUserID(userID);
    }

    // Getter and Setter for password
    public String getPassword() {
        return super.getPassword();
    }

    public void setPassword(String password) {
        super.setPassword(password);
    }

    // Getter and Setter for role
    public String getRole() {
        return super.getRole();
    }

    public void setRole(String role) {
        super.setRole(role);
    }

    // Getter and Setter for gender
    public String getGender() {
        return super.getGender();
    }

    public void setGender(String gender) {
        super.setGender(gender);
    }

    // Getter and Setter for name
    public String getName() {
        return super.getName();
    }

    public void setName(String name) {
        super.setName(name);
    }

    @Override
    public String toString() { // Method automatically invoked when printing staff object
        return getUserID() + "/" + getPassword() + "/" + getName() + "/" + getRole() + "/" + getGender() + "/" + age;
    }
}

