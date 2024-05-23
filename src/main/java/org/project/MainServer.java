package org.project;


import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.StringReader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class MainServer {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();
        ServerSocket server = null;
        try {
            server = new ServerSocket(8080);
        }
        catch (IOException e){
            System.out.println("Couldn't make a socket!!");
            System.exit(-1);
        }

        while (true) {
            try {
                Socket connection = server.accept();
                ServerThread st = new ServerThread(connection);
                executor.execute(st);
            } catch (IOException e) {
                System.out.println("Couldn't connect!!");
                System.exit(-2);
            }
        }
    }
}