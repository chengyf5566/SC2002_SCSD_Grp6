package User;
public class Staff extends User {
    private int age; // Only the age field is specific to Staff

    public Staff(String userID, String password, String role, String gender, String name, int age) {
        super(userID, password, role, gender, name); // Delegate other fields to the User constructor
        this.age = age; // Initialize the age field
    }


    
    // Getters
    public int getAge() {
        return age;
    }

    public String getUserID() {
        return super.getUserID();
    }

    public String getPassword() {
        return super.getPassword();
    }

    public String getRole() {
        return super.getRole();
    }

    public String getGender() {
        return super.getGender();
    }

    public String getName() {
        return super.getName();
    }

    
    //Setters
    public void setAge(int age) {
        this.age = age;
    }

    public void setUserID(String userID) {
        super.setUserID(userID);
    }

    public void setPassword(String password) {
        super.setPassword(password);
    }

    public void setRole(String role) {
        super.setRole(role);
    }

    public void setGender(String gender) {
        super.setGender(gender);
    }

    public void setName(String name) {
        super.setName(name);
    }


    @Override
    public String toString() { // Method automatically invoked when printing staff object
        return getUserID() + "/" + getPassword() + "/" + getName() + "/" + getRole() + "/" + getGender() + "/" + age;
    }
}

