package hospitalManagementSystem;

public class Staff extends User{
    private int age;

    public Staff(String userID, String password, String role, String gender, String name, int age) {
        super(userID, password, role, gender, name);
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
