package com.techelevator.logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    private File logFile;
    private FileWriter output;

    public Logger() {
        logFile = new File("Audit.txt");
        if (logFile.exists()) {
            try {
                output = new FileWriter(logFile, true);
            } catch (IOException e) {
                System.out.println("Log file can't be opened for editing.");;
            }
        } else {
            try {
                output = new FileWriter((logFile));
            } catch (IOException e) {
                System.out.println("Problem creating log file.");
            }
        }
    }

    public void write(String message) {
        try {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss a");
            output.write(formatter.format(now) + " " + message + "\n");
            output.flush();
        } catch (IOException e) {
            System.out.println("Error writing to logfile.");;
        }
    }

}
