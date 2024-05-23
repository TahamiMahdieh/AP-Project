package org.project;

import java.io.*;
import java.net.Socket;

public class MainClient {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Socket socket = new Socket("127.0.0.1", 8080);
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ConsoleImpl display = new ConsoleImpl(socket, out, in);
        display.signUpPage();
    }
}

