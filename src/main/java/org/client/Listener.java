package org.client;

import org.common.Bridge;
import org.common.Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Hashtable;
import java.util.LinkedList;

// this is a thread that listens to client and executes its commands
public class Listener implements Runnable {
    private Socket socket;
    private final Hashtable<Integer, LinkedList<Listener>> listeners = new Hashtable<>();
    private ObjectInputStream input = null;
    private ObjectOutputStream output = null;
    private String jwToken;

    public Listener(Socket socket, ObjectInputStream input, ObjectOutputStream output) throws IOException {
        this.socket = socket;
        this.input = input;
        this.output = output;
        this.jwToken = null;
    }

    @Override
    public void run() {
        try {
            while (socket.isConnected()) {
                Bridge b = (Bridge) input.readObject();
                switch (b.getCommand()) {
                    case SIGN_UP:
                        if (b.getResponse() == Response.SUCCESSFUL){
                            this.jwToken = b.getJwToken(); // it sets this threads token with bridge
                            //ConsoleUtil.printLoginMessage((UserToBeSigned) model.data);
                            //ConsoleImpl.openChatPage(socket, writer,jwToken);
                        } else {
                            //ConsoleUtil.printErrorMSg(model);
                            //ConsoleImpl.openAccountMenu(socket, writer,jwToken);
                        }
                        break;
                }
            }
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }


}
