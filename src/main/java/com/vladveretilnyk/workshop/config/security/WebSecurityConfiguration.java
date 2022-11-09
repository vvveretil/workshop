package com.vladveretilnyk.workshop.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static com.vladveretilnyk.workshop.user.UserRole.ADMIN;
import static com.vladveretilnyk.workshop.user.UserRole.MASTER;
import static com.vladveretilnyk.workshop.user.UserRole.USER;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.cors().disable().csrf().disable()
                .authorizeRequests()

                .antMatchers(HttpMethod.DELETE, "/applications/**").hasAnyRole(ADMIN.name(), USER.name())
                .antMatchers(HttpMethod.GET, "/applications/**").hasAnyRole(ADMIN.name(), MASTER.name(), USER.name())
                .antMatchers(HttpMethod.POST, "/applications/**").hasAnyRole(ADMIN.name(), MASTER.name(), USER.name())

                .antMatchers("/profile/**").hasAnyRole(ADMIN.name(), MASTER.name(), USER.name())
                .antMatchers("/users/**", "/masters/**").hasRole(ADMIN.name())

                .antMatchers("/main.html", "/about", "/contacts", "/login", "/registration", "/css/**", "/js/**").permitAll()

                .anyRequest().authenticated()

                .and()
                .formLogin()
                .loginProcessingUrl("/login")
                .loginPage("/login")
                .permitAll()

                .successHandler((request, response, authentication) -> response.sendRedirect("/profile"))

                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll()

                .and()
                .exceptionHandling()
                .accessDeniedPage("/forbidden")

                .and()
                .build();
    }

}