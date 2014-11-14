package com.junipertech.kickback.networking;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;

public class ClientHandler {

    private Socket clientSocket;

    public void connectToServer(String... address) throws IOException {
        String hostname;
        int port;
        if(address.length == 0 || address.length > 2) {
            hostname = "stevex86.com";
            port = 8080;
        }
        else {
            hostname = address[0];
            port = Integer.parseInt(address[1]);
        }
        clientSocket = new Socket(hostname, port);
        clientSocket.setKeepAlive(true);
    }


    public void addToSendQueue(Packet packet) {

    }
}
