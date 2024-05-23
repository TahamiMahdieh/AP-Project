package org.common;

import java.net.Socket;
import org.common.Bridge;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SendMessage {
    public static void send (Bridge bridge, ObjectOutputStream writer){
        try {
            writer.writeObject(bridge);
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
