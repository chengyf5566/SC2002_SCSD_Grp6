package hospitalManagementSystem;

public class Staff extends User {
    private int age; // Only the age field is specific to Staff

    public Staff(String userID, String password, String role, String gender, String name, int age) {
        super(userID, password, role, gender, name); // Delegate other fields to the User constructor
        this.age = age; // Initialize the age field
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {//method automatically invoked when printing staff object
        return getUserID() + "/" + getPassword() + "/" + getName() + "/" + getRole() + "/" + getGender() + "/" + age;
    }
}
