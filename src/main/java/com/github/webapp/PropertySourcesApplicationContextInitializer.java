package com.github.webapp;

import java.io.IOException;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.support.ResourcePropertySource;

/**
 * <p>PropertySourcesApplicationContextInitializer. </p>
 *
 * @author anavarro - Jun 29, 2016
 *
 */
public final class PropertySourcesApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropertySourcesApplicationContextInitializer.class);

    /**
     * (non-Javadoc)
     *
     * @see org.springframework.context.ApplicationContextInitializer#initialize(org.springframework.context.ConfigurableApplicationContext)
     */
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        LOGGER.info("Adding some additional property sources based on active profiles.");
        final String[] profiles = applicationContext.getEnvironment().getActiveProfiles();
        LOGGER.info("profiles={}", Arrays.asList(profiles));

        final String applicationPropertySource = "classpath:application.properties";
        try {
            applicationContext.getEnvironment().getPropertySources().addFirst(new ResourcePropertySource(applicationPropertySource));
            LOGGER.info(applicationPropertySource + " is loaded.");
        } catch (IOException e) {
            LOGGER.warn("WARN : applicationPropertySource={} is not loaded because {}.", applicationPropertySource, e.getMessage());
        }

        for (final String profile : profiles) {
            final String propertySource = "classpath:application-" + profile + ".properties";
            try {
                applicationContext.getEnvironment().getPropertySources()
                        .addFirst(new ResourcePropertySource(propertySource));
                LOGGER.info(propertySource + " is loaded.");
            } catch (IOException e) {
                LOGGER.warn("WARN : propertySource={} is not loaded because {}.", propertySource, e.getMessage());
            }
        }
    }
}
