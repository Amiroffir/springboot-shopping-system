package com.amiroffir.shoppingsystem.services;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class LogService {
    private static int logCounter = 1;
    private static String logFileName = "log_" + logCounter + ".txt";

    public void logInfo(String message) {
        logToFile("INFO", message);
    }

    public void logError(String message) {
        logToFile("ERROR", message);
    }

    private boolean isFileSizeExceeded(File file) {
        long fileSize = file.length();
        if (fileSize > 1000000) { // 1MB
            return true;
        }
        return false;
    }

    private void createNewLogFile() {
        logFileName = "log_" + ++logCounter + ".txt";
        File newLogFile = new File(logFileName);
        try {
            newLogFile.createNewFile();
        } catch (IOException e) {
            System.out.println("Error creating log file: " + e.getMessage());
        }
    }

    private void writeToFile(String level, String message) {
        String formattedDateTime = OffsetDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        try (PrintWriter writer = new PrintWriter(new FileWriter(logFileName, true))) {
            writer.println(formattedDateTime + " [" + level + "] " + message);
        } catch (IOException e) {
            System.out.println("Error writing to log file: " + e.getMessage());
        }
    }
    private void logToFile(String level, String message) {
        File logFile = new File(logFileName);
        if (!logFile.exists()) {
            createNewLogFile();
        } else {
            if (isFileSizeExceeded(logFile)) {
                createNewLogFile();
            }
        }
        writeToFile(level, message);
    }
}