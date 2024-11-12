package server1111;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static final int SERVER_PORT = 50000;
    public static final String ROOT_DIRECTORY = "html/";

    public static void main (String[] args) {
        try {
            ServerSocket server = new ServerSocket(SERVER_PORT);
            //wait client connection
            System.out.println("Start server");
            Socket clientSocket = server.accept();
            System.out.println("connected" + clientSocket.getInetAddress() + ": " + clientSocket.getPort());
            InputStream inputStream = clientSocket.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String headerLine = bufferedReader.readLine();

            String[] firstLine = headerLine.split("\\s+");

            String method = firstLine[0];
            String uri = firstLine[1];
            String httpVers = firstLine[2];

            System.out.println(method + " " + uri + " " + httpVers);

            if (!httpVers.equals("HTTP/1.2")) {
                String[] response = {"HTTP/1.1 505 OK\r\n", "Server: NewSuperServer\r\n", "\r\n"};
                for (String responseHeaderLine : response) {
                    clientSocket.getOutputStream().write(responseHeaderLine.getBytes());
                    clientSocket.getOutputStream().flush();
                }
                clientSocket.close();
                server.close();
            } else {
                File file = new File(ROOT_DIRECTORY + uri);
                if (!file.exists()) {
                    String[] response = {"HTTP/1.1 200\r\n", "Server: NewSuperServer\r\n", "\r\n"};
                    while (headerLine != null && !headerLine.equals("\r\n")) {
                        headerLine = bufferedReader.readLine();
                        System.out.println(headerLine);
                    }
                } else {
                    String[] response = {"HTTP/1.1 404 Not Found\r\n", "Server: NewSuperServer\r\n", "\r\n"};
                    for (String responseHeaderLine : response) {
                        clientSocket.getOutputStream().write(responseHeaderLine.getBytes());
                        clientSocket.getOutputStream().flush();
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
            //внутренняя ошибка сервера
        }
    }
}
//узнать, как отправлять запрос на MacOS
//String[] response = {"HTTP/1.1 200 OK\r\n",
//                     "Server: GlassFish …\\r\n",
//Content-Language: ru-Ru\r\n
//Content-Type: text/html; charset=utf-8\r\n
//Content-Length: 456\r\n
//\r\n