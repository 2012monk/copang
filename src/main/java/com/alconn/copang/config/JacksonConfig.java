package com.alconn.copang.config;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class JacksonConfig {




    @Bean
    public Jackson2ObjectMapperBuilderCustomizer objectMapper(){

        return builder ->
            Jackson2ObjectMapperBuilder
                    .json()
                    .featuresToDisable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
                    .featuresToDisable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                    .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                    .modules(new JavaTimeModule())
                    .propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);

    }
}
