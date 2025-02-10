package dev.codesoapbox.consumerapp.acknowledgments.config;

import dev.codesoapbox.consumerapp.acknowledgments.application.AcceptAcknowledgmentUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AcknowledgmentsUseCaseBeanConfig {

    @Bean
    AcceptAcknowledgmentUseCase acceptAcknowledgmentUseCase() {
        return new AcceptAcknowledgmentUseCase();
    }
}
