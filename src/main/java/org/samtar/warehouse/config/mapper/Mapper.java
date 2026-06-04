package org.samtar.warehouse.config.mapper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class Mapper {
@Bean
public ObjectMapper objectMapper(){
    return new ObjectMapper();
}
}
