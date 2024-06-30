package org.common;

import java.io.Serializable;

public class GetConnectionReturn implements Serializable {
    private String firstname;
    private String lastname;
    private String email;
    private String status;
    private String myRole;
    public GetConnectionReturn(String firstname, String lastname, String email, String status, String myRole) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.status = status;
        this.myRole = myRole;
    }

    public String getFirstname() {
        return firstname;
    }
    public String getLastname() {
        return lastname;
    }
    public String getStatus() {
        return status;
    }
    public String getMyRole() {
        return myRole;
    }
    public String getEmail() {
        return email;
    }
}
