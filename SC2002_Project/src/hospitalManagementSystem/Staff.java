package hospitalManagementSystem;

public class Staff extends User{
    private int age;
    private String name;
    private String gender;

    public Staff(String userID, String password, String role, String gender, String name, int age) {
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

    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getGender() {
        return gender;
    }

    @Override
    public void setGender(String gender) {
        this.gender = gender;
    }
    
}
