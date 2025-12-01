package models;
// Abstract base class

import models.utils.IDGenerator;

public abstract class User {
    private String id;
    private String name;
    private String email;
    private String role;

    private IDGenerator idGenerator = new IDGenerator();

    public User(int id, String name, String email, String role) {
        this.id = idGenerator.setID('U');
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public void login(){

    }

    //public abstract roleBehavior();
}
