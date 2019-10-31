package com.example.heroes;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Hero {
    private String name, description;
    private int rank;
    public String readTextFile(InputStream inputStream) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        byte buf[] = new byte[1024];
        int len;
        try {
            while ((len = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {

        }
        return outputStream.toString();
    }//take from https://stackoverflow.com/questions/15912825/how-to-read-file-from-res-raw-by-name
}
