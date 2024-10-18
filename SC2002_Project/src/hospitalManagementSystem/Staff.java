package hospitalManagementSystem;

<<<<<<< Updated upstream
public abstract class Staff extends Alluser {
=======
public class Staff extends User {
>>>>>>> Stashed changes
    private String role;
    private int age;

    public Staff(String name, String userID, String password, char gender, String role, int age) {
        super(name, userID, password, gender);
        this.role = role;
        this.age = age;
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
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
