/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.DIGIS01.ACardenasProgramacionNCapas.configuration;

import com.DIGIS01.ACardenasProgramacionNCapas.Service.CustomAuthenticationSuccessHandler;
import com.DIGIS01.ACardenasProgramacionNCapas.Service.UserDetailJPA;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

/**
 *
 * @author digis
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final UserDetailJPA userDetailJPA;
    private final CustomAuthenticationSuccessHandler authenticationSuccessHandler;

    public SecurityConfiguration(UserDetailJPA userDetailJPA1, CustomAuthenticationSuccessHandler authenticationSuccessHandler1) {
        this.userDetailJPA = userDetailJPA1;
        this.authenticationSuccessHandler = authenticationSuccessHandler1;
    }

    @Bean
    public SecurityFilterChain secuFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(configurer -> configurer
                //                .requestMatchers("/usuario/**")

                .requestMatchers("/usuario/**", "/css/**", "/js/**", "/images/**", "/webjars/**")
                .hasAnyRole("Ingeniero", "Residente", "Licenciado")
                .anyRequest().authenticated())
                //                .csrf(csrf -> csrf
                //                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                //                )
                .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
//                .defaultSuccessUrl("/usuario", true)
                .successHandler(authenticationSuccessHandler)
                .usernameParameter("username")
                .passwordParameter("password")
                .permitAll())
                .userDetailsService(userDetailJPA);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
//        return NoOpPasswordEncoder.getInstance();
    }

}
