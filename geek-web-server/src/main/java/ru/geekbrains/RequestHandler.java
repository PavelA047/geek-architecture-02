package ru.geekbrains;

import ru.geekbrains.service.FileService;
import ru.geekbrains.service.SocketService;

import java.io.IOException;
import java.util.Deque;

public class RequestHandler implements Runnable {

    private final SocketService socketService;

    private final FileService fileService;

    public RequestHandler(SocketService socketService, FileService fileService) {
        this.socketService = socketService;
        this.fileService = fileService;
    }

    @Override
    public void run() {
        Deque<String> rawRequest = socketService.readRequest();
        String firstLine = rawRequest.pollFirst();
        String[] parts = firstLine.split(" ");

        if (!fileService.exists(parts[1])) {
            String rawResponse =
                    "HTTP/1.1 404 NOT_FOUND\n" +
                    "Content-Type: text/html; charset=utf-8\n" +
                    "\n" +
                    "<h1>Файл не найден!</h1>";
            socketService.writeResponse(rawResponse);
            return;
        }

        String rawResponse = "HTTP/1.1 200 OK\n" +
                "Content-Type: text/html; charset=utf-8\n" +
                "\n" +
                fileService.readFile(parts[1]);
        socketService.writeResponse(rawResponse);

        try {
            socketService.close();
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }
        System.out.println("Client disconnected!");
    }
}
