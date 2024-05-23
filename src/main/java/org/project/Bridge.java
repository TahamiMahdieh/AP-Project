package org.project;

import java.io.Serializable;

// the class responsible for exchanging objects between server and client
public class Bridge implements Serializable {
    private Commands command = null;
    private Errors errors = null;
    private Object message = null;
    private String jwToken = null;

    public Bridge(Commands command, Object message) {
        this.command = command;
        this.message = message;
    }

    public Bridge(Errors errors) {
        this.errors = errors;
    }

    public Bridge(Commands command, Object message, String jwToken) {
        this.command = command;
        this.message = message;
        this.jwToken = jwToken;
    }

    public Bridge(Commands command, Errors errors, Object message, String jwToken) {
        this.command = command;
        this.errors = errors;
        this.message = message;
        this.jwToken = jwToken;
    }

    public Commands getCommand() {
        return command;
    }
    public void setCommand(Commands command) {
        this.command = command;
    }
    public Object getMessage() {
        return message;
    }
    public String getJwToken() {
        return jwToken;
    }
    public void setJwToken(String jwToken) {
        this.jwToken = jwToken;
    }
    public Errors getErrors() {
        return errors;
    }
    public void setErrors(Errors errors) {
        this.errors = errors;
    }
}
