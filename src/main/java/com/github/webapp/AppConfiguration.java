package com.github.webapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class AppConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppConfiguration.class);

    @Bean
    public String test(@Value("${test:}") String test) {
        LOGGER.info("test={}", test);
        return test;
    }

    @Bean
    @Profile("p1")
    public String p1() {
        LOGGER.info("create p1");
        return "p1";
    }

    @Bean
    @Profile("p2")
    public String p2() {
        LOGGER.info("create p2");
        return "p2";
    }

}
