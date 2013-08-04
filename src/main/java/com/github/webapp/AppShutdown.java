package com.github.webapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.shutdownlistener.ShutdownConfiguration;
import com.googlecode.shutdownlistener.ShutdownUtility;

/**
 * <p>AppShutdown. </p>
 * 
 * @author anavarro - Jun 1, 2013
 * 
 */
public final class AppShutdown {

    /**
     * LOGGER
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AppShutdown.class);

    
    /**
     * Constructor.
     *
     */
    private AppShutdown() {
        super();
    }


    /**
     * main.
     * 
     * @param args
     */
    public static void main(final String[] args) {
        try {
            LOGGER.info("Application is shutdowning ...");
            System.setProperty(AppStartup.SHUTDOWN_CONFIG, AppStartup.SLASH + AppStartup.APP_NAME + AppStartup.PROPERTIES_EXT);
            ShutdownUtility.main(new String[] {ShutdownConfiguration.getInstance().getShutdownWaitCommand()});
            LOGGER.info("Application shutdowned.");
        } catch (Exception e) {
            LOGGER.error("Application can't be shutdowned, e=", e);
        }
    }

}
