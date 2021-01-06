package com.mandziak.model;

public class User {
    private int id;
    private String lastName;
    private String firstName;
    private String middleName;
    private String password;
    private String group;
    private static int current;

    public User() {

    }

    public User(String firstName, String lastName, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User(String firstName, String lastName, String password, String group) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.group = group;
    }

    public User(int id, String firstName, String lastName, String password, String group) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.group = group;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public static int getCurrentUser() {
        return current;
    }

    public static void setCurrentUser(int current) {
        User.current = current;
    }

}
