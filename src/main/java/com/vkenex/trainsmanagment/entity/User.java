package com.vkenex.trainsmanagment.entity;

import com.vkenex.trainsmanagment.entity.enums.UserRole;

public class User {

    private long id;

    private String firstName;
    private String lastName;

    private String login;
    private String passwordHash;

    private UserRole role;

    public User() {}

    public User(long id, String firstName, String lastName, String passwordHash, UserRole role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getLogin() { return login; }

    public String getPasswordHash() {
        return passwordHash;
    }

    public UserRole getRole() {
        return role;
    }


    public void setId(long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}