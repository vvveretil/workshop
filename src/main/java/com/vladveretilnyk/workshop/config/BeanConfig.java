package com.vladveretilnyk.workshop.config;

import com.vladveretilnyk.workshop.user.UserService;
import org.modelmapper.ModelMapper;
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
        return new ModelMapper();
    }

}
