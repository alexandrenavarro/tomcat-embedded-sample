package com.github.webapp;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletException;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.shutdownlistener.ShutdownHandler;
import com.googlecode.shutdownlistener.ShutdownListener;

/**
 * <p>AppStarter. </p>
 * 
 * @author anavarro - Jun 1, 2013
 * 
 */
public final class AppStarter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppStarter.class);

    /**
     * APPLICATION_NAME
     */
    static final String APP_NAME = "tomcat-embedded-sample";

    /**
     * APP_HOME
     */
    static final String APP_HOME = "app.home";

    /**
     * PROPERTIES_EXT
     */
    static final String PROPERTIES_EXT = ".properties";
    
    /**
     * HTTP_PORT
     */
    static final String HTTP_PORT = "httpPort";
    
    /**
     * SHUTDOWN_CONFIG
     */
    static final String SHUTDOWN_CONFIG = "shutdown-listener.configuration";
    
    /**
     * SLASH
     */
    static final String SLASH = "/";

    /**
     * main.
     * 
     * @param args
     */
    public static void main(String[] args) {
        LOGGER.info("Application is starting ...");
        
        final long startTime = System.currentTimeMillis();
        
        // Load application properties 
        final Properties properties = new Properties();
        try {
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(APP_NAME + PROPERTIES_EXT));
        } catch (IOException e) {
            LOGGER.error("ERROR : e=", e);
        }

        // Set shutdown configuration with the default, host and port are used for shutdow
        System.setProperty(SHUTDOWN_CONFIG, SLASH + APP_NAME + PROPERTIES_EXT);

        // Hack to start Tomcat >= 7.0.20 quicker
        System.setProperty("java.security.egd", "file:/dev/./urandom");

        try {
            // Start Tomcat
            final String appHomeDir = System.getProperty(APP_HOME);
            final String webappLocation = new File(((appHomeDir != null) ? appHomeDir : "src" + File.separator + "main") + File.separator + "webapp")
                    .getAbsolutePath();
            
            //TODO improve test 
            final int port = System.getProperty(HTTP_PORT) != null ? Integer.parseInt(System.getProperty(HTTP_PORT)) : 8080;
            final Tomcat tomcat = new Tomcat();
            tomcat.setPort(port);
            tomcat.addWebapp(SLASH + APP_NAME, webappLocation);
            tomcat.start();
            
            // Set Shutdown Hook
            final ShutdownHandler shutdownHandler = new ShutdownHandler();
            shutdownHandler.registerShutdownListener(new ShutdownListener() {

                /**
                 * (non-Javadoc)
                 * 
                 * @see com.googlecode.shutdownlistener.ShutdownListener#shutdown()
                 */
                @Override
                public void shutdown() {
                    try {
                        tomcat.stop();
                        tomcat.destroy();
                    } catch (LifecycleException e) {
                        LOGGER.error("ERROR : e=", e);
                    }
                }
            });
            shutdownHandler.start();
            
            final long stopTime = System.currentTimeMillis();
            LOGGER.info("Application is started in " + (stopTime - startTime) + " ms.");
            
            // Wait for a shutdown request and all shutdown listeners to complete
            shutdownHandler.waitForShutdown();

            // Not need because above
            // tomcat.getServer().await();

        } catch (LifecycleException exception) {
            LOGGER.error("Application can not be started because of a error when Tomcat tried to start (maybe port already used problem).", exception);
        } catch (ServletException exception) {
            LOGGER.error("Application can not be started because of a error when Tomcat tried to start (maybe port already used problem).", exception);
        } catch (Exception exception) {
            LOGGER.error("Application can not be started because of an error when we tried to set Shutdown hook (maybe port already used problem).", exception);
        }
    }
}