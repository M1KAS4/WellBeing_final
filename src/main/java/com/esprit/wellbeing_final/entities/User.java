package com.esprit.wellbeing_final.entities;

import java.util.Date;

public class User {
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Role role;
    private static User Session;

    public User(Long userId, String firstName, String lastName,
                String email, String password, Role role) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;

        this.email = email;
        this.password = password;
        this.role = role;
    }
    public User( String firstName, String lastName,
                 String email, String password, Role role) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Long getId() {
        return userId;
    }

    public void setId(int id) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + email + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + role +
                '}';
    }

    public static void setSession(User session){
        User.Session = session;



    }

    public static User getSession() {
        return Session;
    }
}
