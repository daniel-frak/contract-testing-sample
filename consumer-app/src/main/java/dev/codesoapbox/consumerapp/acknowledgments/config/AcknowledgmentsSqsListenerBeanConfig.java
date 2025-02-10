package dev.codesoapbox.consumerapp.acknowledgments.config;

import dev.codesoapbox.consumerapp.acknowledgments.application.AcceptAcknowledgmentUseCase;
import dev.codesoapbox.consumerapp.acknowledgments.adapters.driving.sqs.MeetingAcknowledgmentSqsListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AcknowledgmentsSqsListenerBeanConfig {

    @Bean
    MeetingAcknowledgmentSqsListener meetingAcknowledgmentSqsListener(
            AcceptAcknowledgmentUseCase acceptAcknowledgmentUseCase) {
        return new MeetingAcknowledgmentSqsListener(acceptAcknowledgmentUseCase);
    }
}
