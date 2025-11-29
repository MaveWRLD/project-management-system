package models;

public class AdminUser extends User{

    public AdminUser(int id, String name, String email) {
        super(id, name, email, "Admin");
    }

}
