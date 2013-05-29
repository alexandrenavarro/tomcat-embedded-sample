package com.github.webapp;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletException;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>TomcatServer. </p>
 *
 * @author anavarro - May 28, 2013
 *
 */
public class TomcatServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(TomcatServer.class);
    
    /**
     * main.
     *
     * @param args
     */
    public static void main(String[] args)
    {
        LOGGER.info("Tomcat Server is starting");
        Properties properties = new Properties();
        try {
            properties.load( Thread.currentThread().getContextClassLoader().getResourceAsStream("tomcat-embedded-sample.properties"));
        } catch (IOException e) {
            LOGGER.error("ERROR : e=", e);
        }
        LOGGER.info("properties=" + properties);
        
        
        final long startTime = System.currentTimeMillis();
        try
       
        {
            final Tomcat tomcat = new Tomcat();
            final String appBase = (new File("./src/main/webapp")).getAbsolutePath();
            tomcat.setPort(8081);
            tomcat.setBaseDir("target");
            tomcat.getHost().setAppBase(appBase);
            tomcat.addWebapp("/tomcat-embedded-sample", appBase);
            tomcat.start();
            LOGGER.info("Tomcat Server started in " + (System.currentTimeMillis() - startTime) + " ms");
            tomcat.getServer().await();
 
        }
        catch (LifecycleException exception)
        {
            LOGGER.error("exception=" + exception);
 
        }
        catch (ServletException exception)
        {
            LOGGER.error("exception=" + exception);
        }
 
    }
}
