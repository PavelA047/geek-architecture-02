package ru.geekbrains;

import ru.geekbrains.service.FileService;
import ru.geekbrains.service.SocketService;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {

    private static String WWW = "C:\\Users\\Павлик\\Desktop\\geek-architecture-02\\geek-architecture-02\\www";

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8088)) {
            System.out.println("Server started!");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected!");

                new Thread(new RequestHandler(
                        new SocketService(socket),
                        new FileService(WWW),
                        new RequestParser(),
                        new ResponseSerializer())).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
