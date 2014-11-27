package com.bramble.kickback.networking;

import com.bramble.kickback.networking.packets.Packet;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ClientHandler {

    private Socket clientSocket;

    public void connectToServer(String... address) throws IOException {
        String hostname;
        int port;
        if(address.length == 0 || address.length > 2) {
            hostname = "stevex86.com";
            port = 8000;
        }
        else {
            hostname = address[0];
            port = Integer.parseInt(address[1]);
        }
        clientSocket = new Socket();
        clientSocket.connect(new InetSocketAddress(hostname, port), 5000);
    }

    public void disconnect() throws IOException {
        clientSocket.close();
    }

    // TODO: Implement a packet send and receive queue to process packets
    public void addToSendQueue(Packet packet) {

    }
}
