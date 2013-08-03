package com.github.webapp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>AppStopper. </p>
 * 
 * @author anavarro - Jun 1, 2013
 * 
 */
public final class AppStopperWithPid {

    /**
     * LOGGER
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AppStopperWithPid.class);

    /**
     * OS
     */
    private static final String OS = System.getProperty("os.name").toLowerCase();

    
    
    /**
     * Constructor.
     *
     */
    private AppStopperWithPid() {
        super();
    }



    /**
     * main.
     * 
     * @param args
     */
    public static void main(final String[] args) {
        LOGGER.info("Application is stopping ...");
        // Stop Application by killing the process on linux
        if (OS.indexOf("linux") >= 0) {
            final String appPidPath = System.getProperty(AppStarterWithPid.APP_HOME) + File.separator + AppStarterWithPid.BIN_DIR + File.separator + AppStarterWithPid.APP_PID;
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
                LOGGER.error("Application can't be stopped because we can't find the " + AppStarterWithPid.APP_PID + " file.");
            }
        } else {
            // TODO to manage Windows Os notably, use jmx or sigar lib or jsw (by preference order)
            LOGGER.error("Application can't be stopped because the script to stop on this OS " + OS + "is not supported. Do it by the hand.");
        }
    }

}
