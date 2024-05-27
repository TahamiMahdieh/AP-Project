package org.common;

import java.io.Serializable;

// the class responsible for exchanging objects between server and client
public class Bridge implements Serializable {
    private Commands command = null;
    private Response response = null;
    private Object message = null;
    private String jwToken = null;

    public Bridge(Commands command, Object message) {
        this.command = command;
        this.message = message;
    }

    public Bridge(Commands command, Object message, String jwToken) {
        this.command = command;
        this.message = message;
        this.jwToken = jwToken;
    }
    public Bridge(Commands command , Response response){
        this.command = command;
        this.response = response;
    }

    public Bridge(Commands command, Response response, Object message, String jwToken) {
        this.command = command;
        this.response = response;
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
    public Response getResponse() {
        return response;
    }
    public void setResponse(Response response) {
        this.response = response;
    }
    @SuppressWarnings("unchecked")
    public <T> T get() {
        return (T) message;
    }
}
