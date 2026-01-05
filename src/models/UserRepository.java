package models;

import java.util.List;

public class UserRepository {

    List<User> users;

    public UserRepository(List<User> users) {
        this.users = users;
    }

    public UserRepository() {
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
