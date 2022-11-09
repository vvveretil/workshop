package com.vladveretilnyk.workshop.config;

import com.vladveretilnyk.workshop.application.Application;
import com.vladveretilnyk.workshop.application.request.ApplicationCreateRequest;
import com.vladveretilnyk.workshop.user.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class BeanConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserService userService) {
        return userService::findByUsername;
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(applicationCreateRequestApplicationPropertyMap());

        return modelMapper;
    }

    @Bean
    public PropertyMap<ApplicationCreateRequest, Application> applicationCreateRequestApplicationPropertyMap() {
        return new PropertyMap<>() {
            @Override
            protected void configure() {
                skip(destination.getId());
            }
        };
    }
}
