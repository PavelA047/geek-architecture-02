package ru.geekbrains;

import java.io.PrintWriter;

public class ResponseHandler {

    public static void responseNotFound(PrintWriter output) {
        output.println("HTTP/1.1 404 NOT_FOUND");
        output.println("Content-Type: text/html; charset=utf-8");
        output.println();
        output.println("<h1>Файл не найден!</h1>");
        output.flush();
    }

    public static void responseOk(PrintWriter output) {
        output.println("HTTP/1.1 200 OK");
        output.println("Content-Type: text/html; charset=utf-8");
        output.println();
    }
}
