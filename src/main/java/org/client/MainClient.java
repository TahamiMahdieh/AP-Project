package org.client;

import controller.LinkedInApplication;
import javafx.application.Application;

public class MainClient {
    public static void main(String[] args) {
//        try {
//            Socket socket = new Socket("127.0.0.1", 8080);
//            ObjectOutputStream writer = new ObjectOutputStream(socket.getOutputStream());

//            ObjectInputStream reader = new ObjectInputStream(socket.getInputStream());
//            ExecutorService executor = Executors.newCachedThreadPool();
//            ConsolePresenter.showWelcomePage(socket, writer,null);
//            Listener listeningUser = new Listener(socket, reader, writer);
//            executor.execute(listeningUser);
//        }catch (IOException e){
//            System.out.println("connection failed");
//        }
        Application.launch(LinkedInApplication.class);
    }
}