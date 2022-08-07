package ru.geekbrains;

import ru.geekbrains.domain.HttpResponse;

public class ResponseSerializer {

    public String serialize(HttpResponse response) {

        String firstRaw = null;

        if (response.getStatusCode() == 200) {
            firstRaw = "HTTP/1.1 200 OK";
        } else if (response.getStatusCode() == 404) {
            firstRaw = "HTTP/1.1 404 NOT_FOUND";
        }
        return firstRaw + "\n"
                + response.getHeaders().entrySet().stream().iterator().next().getKey() + ": "
                + response.getHeaders().entrySet().stream().iterator().next().getValue() + "\n"
                + "\n"
                + response.getBody();
    }
}
