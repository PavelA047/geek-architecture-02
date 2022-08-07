package ru.geekbrains;

import ru.geekbrains.domain.HttpRequest;

import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class RequestParser {

    public HttpRequest parse(Deque<String> rawRequest) {

        HttpRequest httpRequest = new HttpRequest();

        String[] firstRawParts = rawRequest.pollFirst().split(" ");
        httpRequest.setMethod(firstRawParts[0]);
        httpRequest.setPath(firstRawParts[1]);

        Map<String, String> headers = new HashMap<>();
        rawRequest.stream().filter(r -> !r.isEmpty()).forEach((raw) -> {
            String[] parts = raw.split(" ", 2);
            headers.put(parts[0], parts[1]);
        });
        httpRequest.setHeaders(headers);
        return httpRequest;
    }
}
