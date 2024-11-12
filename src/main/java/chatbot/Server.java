//Задание 1:
//Создать чат-бот в архитектуре клиент-сервер
//Клиент отправляет вопрос, сервер дает "осмысленный" ответ (не менее 5 вариантов вопросов)
//Сервер должен остановиться при появлении команды "Exit"

package chatbot;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {

    public static final int SERVER_PORT = 50000;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
            System.out.println("Сервер запущен на порту...");

            // Ожидание подключения клиента
            Socket clientSocket = serverSocket.accept();
            System.out.println("Клиент подключен!");

            // Потоки для общения с клиентом
            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); //для получения ответа от клиента
            PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true); //для отправки ответа клиенту

            String message;
            while (true) {
                message = input.readLine();  // Получение сообщения от клиента

                if (message == null || message.equalsIgnoreCase("Exit")) {
                    break;  // Завершаем работу сервера, если клиент отправил "Exit"
                }

                String response = generateResponse(message);
                output.println(response);  // Отправка ответа клиенту
            }

            System.out.println("Клиент отключился.");
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Метод для генерации ответа на основе вопроса клиента
    private static String generateResponse(String message) {
        message = message.toLowerCase();

        if (message.contains("привет")) {
            return "Привет! Как я могу помочь?";
        } else if (message.contains("как дела")) {
            return "Все хорошо! А как у тебя?";
        } else if (message.contains("что ты умеешь")) {
            return "Я могу отвечать на вопросы и помогать с задачами!";
        } else if (message.contains("какая погода")) {
            return "Извини, я не могу узнать погоду, но могу помочь с чем-то другим.";
        } else if (message.contains("кто ты")) {
            return "Я чат-бот, созданный для общения с пользователями.";
        } else {
            return "Извини, я не понял твой вопрос.";
        }
    }
}