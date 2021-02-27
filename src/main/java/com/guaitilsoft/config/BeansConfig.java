package com.guaitilsoft.config;

import com.guaitilsoft.localDate.LocalDateFormatter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.format.Formatter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import java.time.LocalDateTime;

@Configuration
public class BeansConfig {
    @Bean
    @Primary
    public Formatter<LocalDateTime> localDateFormatter() {
        return new LocalDateFormatter();
    }

    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver multiPartResolver() {
        return new CommonsMultipartResolver();
    }
}
