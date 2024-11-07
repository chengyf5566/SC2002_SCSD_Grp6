package hospitalManagementSystem;

public class Staff extends User{
    private int age;
    private String name;
    private char gender;

    public Staff(String userID, String password, String role, char gender, String name, int age) {
        super(userID, password, role);
        this.gender = gender;
        this.name = name;
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }
    
}
