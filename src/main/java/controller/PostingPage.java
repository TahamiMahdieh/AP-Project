package controller;

import java.io.ObjectOutputStream;
import java.net.Socket;

public class PostingPage {
    private Socket socket;
    private ObjectOutputStream writer;
    private String email;

    public Socket getSocket() {
        return socket;
    }
    public void setSocket(Socket socket) {
        this.socket = socket;
    }
    public ObjectOutputStream getWriter() {
        return writer;
    }
    public void setWriter(ObjectOutputStream writer) {
        this.writer = writer;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
