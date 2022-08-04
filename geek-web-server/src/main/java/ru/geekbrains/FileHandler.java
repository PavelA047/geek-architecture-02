package ru.geekbrains;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileHandler {

    public static void responseFromFile(String folder, String filename, PrintWriter output) throws IOException {
        Path path = Paths.get(folder, filename);

        if (!Files.exists(path)) {
            ResponseHandler.responseNotFound(output);
        }

        ResponseHandler.responseOk(output);

        Files.newBufferedReader(path).transferTo(output);
    }
}
