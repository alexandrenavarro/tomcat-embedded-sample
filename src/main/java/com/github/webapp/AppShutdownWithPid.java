package com.github.webapp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>AppShutdownWithPid. </p>
 * 
 * @author anavarro - Jun 1, 2013
 * 
 */
public final class AppShutdownWithPid {

    /**
     * LOGGER
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AppShutdownWithPid.class);

    /**
     * OS
     */
    private static final String OS = System.getProperty("os.name").toLowerCase();

    
    
    /**
     * Constructor.
     *
     */
    private AppShutdownWithPid() {
        super();
    }



    /**
     * main.
     * 
     * @param args
     */
    public static void main(final String... args) {
        LOGGER.info("Application is stopping ...");
        // Stop Application by killing the process on linux
        if (OS.indexOf("linux") >= 0) {
            final String appPidPath = System.getProperty(AppStartupWithPid.APP_HOME) + File.separator + AppStartupWithPid.BIN_DIR + File.separator + AppStartupWithPid.APP_PID;
            final File appPidFile = new File(appPidPath);
            if (appPidFile.exists()) {
                String command = "";
                try {
                    final BufferedReader bufferedReader = new BufferedReader(new FileReader(appPidFile));
                    command = "kill " + bufferedReader.readLine();
                    Process process = Runtime.getRuntime().exec(command);
                    process.waitFor();
                    if (process.exitValue() == 0) {
                        appPidFile.delete();
                        LOGGER.info("Application stopped.");
                    } else {
                        LOGGER.error("Application can't be stopped because failed to try to kill application with command=" + command);
                    }
                } catch (IOException e) {
                    LOGGER.error("Application can't be stopped because to launch successfully this command " + command + ", e=", e);
                } catch (InterruptedException e) {
                    LOGGER.error("Application can't be stopped because to launch successfully this command " + command + ", e=", e);
                }
            } else {
                LOGGER.error("Application can't be stopped because we can't find the " + AppStartupWithPid.APP_PID + " file.");
            }
        } else {
            // TODO to manage Windows Os notably, use jmx or sigar lib or jsw (by preference order)
            LOGGER.error("Application can't be stopped because the script to stop on this OS " + OS + "is not supported. Do it by the hand.");
        }
    }

}
