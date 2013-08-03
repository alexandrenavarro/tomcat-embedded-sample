package com.github.webapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.shutdownlistener.ShutdownConfiguration;
import com.googlecode.shutdownlistener.ShutdownUtility;

/**
 * <p>AppStopper. </p>
 * 
 * @author anavarro - Jun 1, 2013
 * 
 */
public final class AppStopper {

    /**
     * LOGGER
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AppStopper.class);

    
    /**
     * Constructor.
     *
     */
    private AppStopper() {
        super();
    }


    /**
     * main.
     * 
     * @param args
     */
    public static void main(final String[] args) {
        try {
            System.setProperty(AppStarter.SHUTDOWN_CONFIG, AppStarter.SLASH + AppStarter.APP_NAME + AppStarter.PROPERTIES_EXT);
            ShutdownUtility.main(new String[] {ShutdownConfiguration.getInstance().getShutdownWaitCommand()});
            LOGGER.info("Application stopped.");
        } catch (Exception e) {
            LOGGER.error("Application can't be stopped, e=", e);
        }
    }

}
