package dev.codesoapbox.producerapp.greetings.config;

import dev.codesoapbox.producerapp.greetings.adapters.driving.http.mappers.GreetResponseDtoMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GreetControllerBeanConfig {

    @Bean
    GreetResponseDtoMapper greetResponseDtoMapper() {
        return Mappers.getMapper(GreetResponseDtoMapper.class);
    }
}
