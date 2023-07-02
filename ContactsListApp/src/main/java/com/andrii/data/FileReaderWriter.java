package com.andrii.data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileReaderWriter {
    private static final Path FILE_PATH = Paths.get("./testFile.txt");;

    public static String read() {
        try {
            if(!Files.exists(FILE_PATH))
                Files.createFile(FILE_PATH);

            return Files.readString(FILE_PATH);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return "";
        }
    }

    public static boolean write(String content) {
        try {
            Files.writeString(FILE_PATH, content);
            return true;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
