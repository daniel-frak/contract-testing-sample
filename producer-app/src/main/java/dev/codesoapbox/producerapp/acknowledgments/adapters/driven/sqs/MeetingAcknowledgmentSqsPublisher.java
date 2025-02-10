package dev.codesoapbox.producerapp.acknowledgments.adapters.driven.sqs;

import dev.codesoapbox.producerapp.acknowledgments.application.MeetingAcknowledgmentPublisher;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MeetingAcknowledgmentSqsPublisher implements MeetingAcknowledgmentPublisher {

    private final SqsTemplate sqsTemplate;
    private final String queueName;

    public void publishMeetingAcknowledgment(String name) {
        sqsTemplate.send(to -> to
                .queue(queueName)
                .payload(new MeetingAcknowledgmentSqsDto("Met " + name)));
    }
}
