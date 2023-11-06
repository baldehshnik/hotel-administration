package com.vd.university.util.log;

import com.vd.university.di.annotation.SingletonScope;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SingletonScope
public class Log {

    private static Log log;

    private static final String fileName = "log/log.txt";

    private final FileWriter fileWriter;
    private final BufferedWriter bufferedWriter;

    private Log() throws IOException {
        File logFile = new File(fileName);
        if (!logFile.exists()) {
            logFile.createNewFile();
        } else {
            new FileWriter(logFile, false).close();
        }

        fileWriter = new FileWriter(logFile, true);
        bufferedWriter = new BufferedWriter(fileWriter);
    }

    public static void init() throws IOException {
        log = new Log();
    }

    public <T> void warning(String message, Class<T> clazz) {
        String timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        String logMessage = String.format(
                "WARNING - %s\nA warning occurred in the class - %s.\nMESSAGE: %s",
                timeFormat, clazz.getName(), message
        );

        try {
            bufferedWriter.write(logMessage);
            bufferedWriter.newLine();
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException ignored) {}
    }

    public void info(String message) {
        String timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        String logMessage = String.format(
                "INFO - %s\nMESSAGE: %s",
                timeFormat, message
        );

        try {
            bufferedWriter.write(logMessage);
            bufferedWriter.newLine();
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException ignored) {}
    }

    public <T> void error(String message, Class<T> clazz) {
        String timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        String logMessage = String.format(
                "ERROR - %s\nAn error occurred in the class - %s.\nMESSAGE: %s",
                timeFormat, clazz.getName(), message
        );

        try {
            bufferedWriter.write(logMessage);
            bufferedWriter.newLine();
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException ignored) {}
    }

    public void close() {
        try {
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (fileWriter != null) {
                fileWriter.close();
            }
        } catch (IOException ignored) {}
    }

    public static Log getInstance() {
        return log;
    }
}