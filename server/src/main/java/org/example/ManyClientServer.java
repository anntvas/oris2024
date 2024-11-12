package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ManyClientServer {
    public static final int SERVER_PORT = 50000;

    public static void main (String[] args) {
        try {
            ServerSocket server = new ServerSocket(SERVER_PORT);
            System.out.println("Start server");
            while (true) {
                //wait client connection
                System.out.println("wait client connection");
                Socket clientSocket = server.accept();
                //создать параллельный поток
                ClientHandler handler = new ClientHandler(clientSocket);
                new Thread(handler).start();
        //        server.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
