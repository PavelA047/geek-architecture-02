package ru.geekbrains;

import ru.geekbrains.domain.HttpRequest;
import ru.geekbrains.domain.HttpResponse;
import ru.geekbrains.service.FileService;
import ru.geekbrains.service.SocketService;

import java.io.IOException;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class RequestHandler implements Runnable {

    private final SocketService socketService;

    private final FileService fileService;

    private final RequestParser requestParser;

    private final ResponseSerializer responseSerializer;

    public RequestHandler(
            SocketService socketService,
            FileService fileService,
            RequestParser requestParser,
            ResponseSerializer responseSerializer) {
        this.socketService = socketService;
        this.fileService = fileService;
        this.requestParser = requestParser;
        this.responseSerializer = responseSerializer;
    }

    @Override
    public void run() {
        Deque<String> rawRequest = socketService.readRequest();
        HttpRequest httpRequest = requestParser.parse(rawRequest);

        if (!fileService.exists(httpRequest.getPath())) {
            String rawResponse = responseSerializer.serialize(new HttpResponse(
                    404,
                    new HashMap<>(Map.of("Content-Type", "text/html; charset=utf-8")),
                    "<h1>Файл не найден!</h1>"
            ));
            socketService.writeResponse(rawResponse);
            return;
        }

        String rawResponse = responseSerializer.serialize(new HttpResponse(
                200,
                new HashMap<>(Map.of("Content-Type", "text/html; charset=utf-8")),
                fileService.readFile(httpRequest.getPath())
        ));
        socketService.writeResponse(rawResponse);

        try {
            socketService.close();
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }
        System.out.println("Client disconnected!");
    }
}
