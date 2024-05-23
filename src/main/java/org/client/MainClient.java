package org.client;

import org.consoleInterface.ConsolePresenter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("127.0.0.1", 8080);
            ObjectOutputStream writer = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream reader = new ObjectInputStream(socket.getInputStream());
            ExecutorService executor = Executors.newCachedThreadPool();
            ConsolePresenter.showWelcomePage(socket, writer,null);
            Listener listeningUser = new Listener(socket, reader, writer);
            executor.execute(listeningUser);
        }catch (IOException e){
            System.out.println("connection failed");
        }
    }
}