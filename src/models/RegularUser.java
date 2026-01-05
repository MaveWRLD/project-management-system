package models;

public class RegularUser extends User {

    public RegularUser(String name, String email) {
        super(name, email);
    }

    public RegularUser() {
    }

    @Override
    public String getRole() {
        return "Regular User";
    }
}
