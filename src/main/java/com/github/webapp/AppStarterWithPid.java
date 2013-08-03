package com.github.webapp;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletException;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>AppStarter. </p>
 * 
 * @author anavarro - Jun 1, 2013
 * 
 */
public final class AppStarterWithPid {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppStarterWithPid.class);

    /**
     * APPLICATION_NAME
     */
    private static final String APP_NAME = "tomcat-embedded-sample";

    /**
     * BIN_FOLDER
     */
    static final String BIN_DIR = "bin";

    
    /**
     * APP_HOME
     */
    static final String APP_HOME = "app.home";
    /**
     * APP_PID
     */
    static final String APP_PID = "app.pid";

    
    
    /**
     * Constructor.
     *
     */
    private AppStarterWithPid() {
        // TODO Auto-generated constructor stub
        super();
        
    }



    /**
     * main.
     * 
     * @param args
     */
    public static void main(String[] args) {
        
        //TODO manage 2 start without stop
        //TODO manage launch in background
        
        LOGGER.info("Application is starting ...");
        final Properties properties = new Properties();
        try {
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(APP_NAME + ".properties"));
        } catch (IOException e) {
            LOGGER.error("ERROR : e=", e);
        }
        final long startTime = System.currentTimeMillis();
        try {
            
            
            // Start Tomcat
            final String appHomeDir = System.getProperty(APP_HOME);
            final String webappLocation = new File(appHomeDir + File.separator + "webapp").getAbsolutePath();
            final int port = 8080;
            final Tomcat tomcat = new Tomcat();
            tomcat.setPort(port);
            tomcat.addWebapp("/" + APP_NAME, webappLocation);
            tomcat.start();
            final long stopTime = System.currentTimeMillis();
            LOGGER.info("Application is started in " + (stopTime - startTime) + " ms.");

            // Write pid in a file on linux OS
            final String appPid = System.getProperty(APP_PID);
            if (appPid != null) {
                final File appPidFile = new File(appHomeDir + File.separator + BIN_DIR + File.separator + APP_PID);
                FileWriter fileWriter = null;
                try {
                    fileWriter = new FileWriter(appPidFile);
                    fileWriter.write(appPid);
                } catch (IOException e) {
                    LOGGER.error("ERROR : e=", e);
                } finally {
                    if (fileWriter != null) {
                        try {
                            fileWriter.close();
                        } catch (IOException ioe) {
                            LOGGER.error("ERROR : e=", ioe);
                        }
                    }
                }
            }
            tomcat.getServer().await();
            
            
            
        } catch (LifecycleException exception) {
            LOGGER.error("Impossible to start Application.", exception);
        } catch (ServletException exception) {
            LOGGER.error("Impossible to start Application.", exception);
        }
    }
}
