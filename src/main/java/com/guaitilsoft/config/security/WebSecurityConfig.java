package com.guaitilsoft.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${guaitil-client.cors}")
    private String urlCors;

    private final TokenProvider tokenProvider;

    @Autowired
    public WebSecurityConfig(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // Disable CSRF (cross site request forgery)
        http.csrf().disable();

        // No session will be created or used by spring app.security
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Entry points
        http.addFilterBefore(new WebSecurityCorsFilter(), ChannelProcessingFilter.class);

        http.authorizeRequests()//

                .antMatchers("/**/*.{js,html,css}").permitAll()
                .antMatchers("/auth/login", "/auth/register").permitAll()
                .antMatchers("/api/multimedia/load/**", "/api/gallery", "/api/multimedia", "api/products/local-id/*").permitAll()
                .antMatchers("/api/locals/**").permitAll()
                .antMatchers("/api/activities/**").permitAll()
                .antMatchers("/api/products/state/**").permitAll()
                .antMatchers("/api/**", "/auth/**").authenticated()
                .requestMatchers(CorsUtils::isCorsRequest).permitAll()
                .anyRequest().permitAll();

        // Apply JWT
        http.apply(new TokenFilterConfigurer(this.tokenProvider));

        // Optional, if you want to test the API from a browser
        http.httpBasic();
        http.cors();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList(this.urlCors));
        configuration.setAllowedMethods(Arrays.asList("GET","POST", "PUT", "DELETE"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers("/v2/api-docs")
                .antMatchers("/configuration/ui")
                .antMatchers("/swagger-resources/**")
                .antMatchers("/configuration/security")
                .antMatchers("/swagger-ui.html")
                .antMatchers("/webjars/**");

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManager();
    }
}