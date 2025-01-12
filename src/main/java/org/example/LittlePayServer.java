package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class LittlePayServer {

    private final LittlePayClientHandler littlePayClientHandler = new LittlePayClientHandler();
    private ServerSocket serverSocket;

    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
            while (true) {
                try (Socket socket = serverSocket.accept()) {
                    littlePayClientHandler.run(socket);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            stop();
        }
    }

    public void stop() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        LittlePayServer socketServer = new LittlePayServer();
        socketServer.start(8888);
    }
}