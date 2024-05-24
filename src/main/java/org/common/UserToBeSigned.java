package org.common;

import java.io.Serializable;

public class UserToBeSigned implements Serializable {
    private String password;
    private String firstName;
    private String lastName;
    private String email;

    public UserToBeSigned() {
        email = null;
        lastName = null;
        firstName = null;
        password = null;
    }

    public UserToBeSigned(String password, String firstName, String lastName, String email) {
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}

