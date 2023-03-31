package com.example.UserManager.core;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
//@ConfigurationProperties
public class YAMLConfig {

    private String name;
    private String environment;
    private boolean enabled;

    // standard getters and setters

}
