package com.guaitilsoft.config;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.guaitilsoft.localDate.LocalDateFormatter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.context.annotation.Primary;
import org.springframework.format.Formatter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

@Configuration
@AutoConfigureBefore({JacksonAutoConfiguration.class})
public class BeansConfig {
    @Bean
    @Primary
    public Formatter<LocalDate> localDateFormatter() {
        return new LocalDateFormatter();
    }

    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver multiPartResolver() {
        return new CommonsMultipartResolver();
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jacksonObjectMapperBuilderCustomizer() {
        return jacksonObjectMapperBuilder -> {
            final String dateTimeFormat = "yyyy-MM-dd HH:mm";
            jacksonObjectMapperBuilder
                    .serializers(
                            new LocalDateTimeSerializer(
                                    DateTimeFormatter.ofPattern(dateTimeFormat)))
                    .deserializers(
                            new LocalDateTimeDeserializer(
                                    DateTimeFormatter.ofPattern(dateTimeFormat)));
        };
    }

    @Bean
    public JavaMailSenderImpl mailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

        javaMailSender.setProtocol("smtp");
        javaMailSender.setHost("smtp.gmail.com");
        javaMailSender.setPort(587);
        javaMailSender.setUsername("guaitiltour.cr@gmail.com");
        javaMailSender.setPassword("Chorotega17");
        Properties props = javaMailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.debug", "true");

        return javaMailSender;
    }
}
