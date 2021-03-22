package de.workshops.bookdemo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties(prefix = "bookdemo")
@Data
public class ApplicationConfiguration {

    private String param1;
    
    private int param2;
    
    private Nested nested;
    
    @Data
    public static class Nested {
        private String param3;
    }
}
