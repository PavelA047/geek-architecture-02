package ru.geekbrains;

public class ParsingHandler {

    public static String getFileNameFromRequest(String firstLine) {
        String[] parts = firstLine.split(" ");
        return parts[1];
    }
}
