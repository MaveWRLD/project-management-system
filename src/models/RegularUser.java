package models;

public class RegularUser extends User {

    public RegularUser(int id, String name, String email) {
        super(id, name, email, "Regular_User");
    }
}
