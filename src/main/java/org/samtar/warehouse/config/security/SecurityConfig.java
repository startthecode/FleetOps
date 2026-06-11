package org.samtar.warehouse.config.security;

import java.util.List;

import org.samtar.warehouse.security.jwt.JwtFilterChain;
import org.samtar.warehouse.user.service.imp.UserDetailImp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.micrometer.observation.autoconfigure.ObservationProperties.Http;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    private JwtFilterChain jwtFilterChain;
    private final ObjectMapper mapper;
    private final UserDetailImp userDetailImp;
    private final int passwordStrength;
    private final List<String> unprotectedEndpoints = List.of("/api/v1/auth/**",
                                     "/api/v1/vendor/**",
                                     "/api/v1/driver/**",
                                     "/api/testing/unprotected");

    public SecurityConfig(ObjectMapper mapper, UserDetailImp userDetailImp,
                          @Value("${password.strength}") int passwordStrength,JwtFilterChain jwtFilterChain) {
        this.mapper = mapper;
        this.userDetailImp = userDetailImp;
        this.passwordStrength = (int) passwordStrength;
        this.jwtFilterChain = jwtFilterChain;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(this.passwordStrength);
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration cfg){
        return cfg.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailImp);
        provider.setPasswordEncoder(this.passwordEncoder());
        return provider;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity){
        return httpSecurity.csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(auth->
            auth.requestMatchers(unprotectedEndpoints.toArray(String[]::new))
            .permitAll()
            .anyRequest()
            .authenticated()
        ).sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtFilterChain, UsernamePasswordAuthenticationFilter.class)
        .exceptionHandling(ex->ex.authenticationEntryPoint((req,res,excep)->{
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            res.setContentType(MediaType.APPLICATION_JSON_VALUE);
            mapper.writeValue(res.getWriter(),"Unauthorized user");
        }))
        .build();
    }

}
