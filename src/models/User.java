package models;
// Abstract base class

import utils.IDGenerator;

public abstract class User {
    private String id;
    private String name;
    private String email;
    private String role;

    private IDGenerator idGenerator = new IDGenerator();

    public User(String name, String email, String role) {
        this.id = idGenerator.setID('U');
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public void login(){

    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public IDGenerator getIdGenerator() {
        return idGenerator;
    }

    //public abstract roleBehavior();
}
