package dev.codesoapbox.producerapp.acknowledgments.config;

import dev.codesoapbox.producerapp.acknowledgments.adapters.driven.sqs.MeetingAcknowledgmentSqsPublisher;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AcknowledgmentsSqsPublisherBeanConfig {

    @Bean
    MeetingAcknowledgmentSqsPublisher meetingAcknowledgmentSqsPublisher(
            SqsTemplate sqsTemplate,
            @Value("${producer-app.queue-names.meeting-acknowledgments}") String meetingAcknowledgmentsQueueName) {
        return new MeetingAcknowledgmentSqsPublisher(sqsTemplate, meetingAcknowledgmentsQueueName);
    }
}
