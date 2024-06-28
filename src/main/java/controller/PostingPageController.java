package controller;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class PostingPageController {
    private Socket socket;
    private ObjectOutputStream writer;
    private ObjectInputStream reader;
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
    public ObjectInputStream getReader() {
        return reader;
    }
    public void setReader(ObjectInputStream reader) {
        this.reader = reader;
    }
}
