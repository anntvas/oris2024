package chatbot;

import java.io.*;
import java.net.*;

public class Client {

    public static void main(String[] args) {
        try (Socket socket = new Socket("127.0.0.1", 50000)) {
            System.out.println("Подключение к серверу установлено...");

            // Потоки   для общения с сервером
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in)); //для получения данных от пользователя с консоли
            BufferedReader serverInput = new BufferedReader(new InputStreamReader(socket.getInputStream())); //для чтения ответа с сервера
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true); //для отправки данных серверу

            String message;
            while (true) {
                System.out.print("Введите ваш вопрос: ");
                message = input.readLine();  // Получаем вопрос от пользователя
                output.println(message);  // Отправляем вопрос серверу

                if (message.equalsIgnoreCase("Exit")) {
                    break;  // Завершаем программу, если введена команда "Exit"
                }

                // Чтение ответа от сервера
                String response = serverInput.readLine();
                System.out.println("Ответ от сервера: " + response);
            }
            System.out.println("Выход из программы.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}