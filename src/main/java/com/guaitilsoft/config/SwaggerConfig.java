package com.guaitilsoft.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

@Configuration
@EnableSwagger2
public class SwaggerConfig implements WebMvcConfigurer{


    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(Predicates.not(PathSelectors.regex("/error")))
                .build()
                .apiInfo(metadata())
                .useDefaultResponseMessages(false)
                .securitySchemes(new ArrayList<>(Collections.singletonList(new ApiKey("Bearer %token", "Authorization", "Header"))))
                .genericModelSubstitutes(Optional.class);

    }

    private ApiInfo metadata() {
        return new ApiInfoBuilder()
                .title("GuaitilSoft API")
                .description("Este es un API que maneja los procesos del Guaitil y presenta una autenticacion con JWT authentication app.service. Una vez iniciado sesión satifactoriamente se obtiene un token")
                .version("0.0.1")
                .license("MIT License").licenseUrl("http://opensource.org/licenses/MIT")
                .build();
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
