package org.server;

import org.common.Bridge;
import org.common.Commands;
import org.common.User;
import org.common.UserToBeSigned;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable{
    private Socket connectionSocket;
    private static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private ObjectOutputStream writer = null;
    private ObjectInputStream reader = null;

    public ClientHandler(Socket connectionSocket) {
        this.connectionSocket = connectionSocket;
        try{
            writer = new ObjectOutputStream(connectionSocket.getOutputStream());
            reader = new ObjectInputStream(connectionSocket.getInputStream());
        }
        catch (IOException io){
            io.printStackTrace();
        }
        clientHandlers.add(this);
    }

    @Override
    public void run (){
        try {
            while (connectionSocket.isConnected()){
                Bridge bridge = (Bridge) reader.readObject();
                switch (bridge.getCommand()){
                    case SIGN_UP -> {
                        UserToBeSigned user = bridge.get();
                        Bridge res = PagesToBeShownToUser.signUpPage(user);
                        res.makeJwToken(user.getUsername());
                        write(res);
                        System.out.println("okkkkkkkkkkkkkkkkk");
                    }
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void write(Bridge bridge) {
        if (bridge == null) return;
        try {
            writer.writeObject(bridge);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
