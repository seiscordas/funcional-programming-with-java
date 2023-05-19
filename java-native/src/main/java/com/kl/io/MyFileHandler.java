package com.kl.io;

import java.io.*;

public class MyFileHandler {
    File file;
    FileOutputStream fileOutputStream;
    BufferedReader bufferedReader = null;

    public MyFileHandler() {
        createTxtFile();
        writeTxtFile();
        readTxtFile();
    }

    private void createTxtFile() {
        try {
            file = new File("myFileTest.txt");
            if (file.createNewFile()) {
                System.out.println("File is created!");
            } else {
                System.out.println("File already exists!");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void writeTxtFile() {
        String text = "this is the content written on the txt file.";
        try {
            fileOutputStream = new FileOutputStream(file);
            byte[] textBytes = text.getBytes();
            fileOutputStream.write(textBytes);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        System.out.println("text was written on the file: " + file.getName());
    }

    private void readTxtFile() {
        try {
            String currentLine;

            bufferedReader = new BufferedReader(new FileReader("myFileTest.txt"));

            while ((currentLine = bufferedReader.readLine()) != null) {
                System.out.println(currentLine);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
