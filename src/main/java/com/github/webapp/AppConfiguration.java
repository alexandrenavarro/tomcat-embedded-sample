package com.github.webapp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class AppConfiguration {

    
    
    @Bean
    public String test() {
        return "Test";
    }
    
    @Bean
    @Profile("p1")
    public String p1() {
        return "p1";
    }
    
    @Bean
    @Profile("p2")
    public String p2() {
        return "p2";
    }

}
