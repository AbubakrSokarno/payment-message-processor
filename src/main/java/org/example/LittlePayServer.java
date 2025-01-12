package org.example;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

@Service
public class LittlePayServer {

    private final LittlePayClientHandler littlePayClientHandler;
    private ServerSocket serverSocket;

    public LittlePayServer(LittlePayClientHandler littlePayClientHandler) {
        this.littlePayClientHandler = littlePayClientHandler;
    }

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
}