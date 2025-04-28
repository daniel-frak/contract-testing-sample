package dev.codesoapbox.consumerapp.acknowledgments.adapters.driving.sqs;

import dev.codesoapbox.consumerapp.acknowledgments.application.AcceptAcknowledgmentUseCase;
import dev.codesoapbox.consumerapp.testing.queues.SqsListenerContractTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.contract.stubrunner.StubTrigger;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

import java.time.Duration;

import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.verify;

@SqsListenerContractTest
class MeetingAcknowledgmentSqsListenerIT {

    private static final String QUEUE_NAME = MeetingAcknowledgmentSqsListener.ACKNOWLEDGMENTS_QUEUE;

    @Autowired
    private SqsAsyncClient sqsAsyncClient;

    @Autowired
    private StubTrigger stubTrigger;

    @Autowired
    private AcceptAcknowledgmentUseCase acceptAcknowledgmentUseCase;

    @AfterEach
    void tearDown() {
        String queueUrl = sqsAsyncClient.getQueueUrl(request -> request.queueName(QUEUE_NAME)).join().queueUrl();
        sqsAsyncClient.purgeQueue(request -> request.queueUrl(queueUrl)).join();
    }

    @Test
    void shouldAcceptMeetingAcknowledgment() {
        stubTrigger.trigger("acknowledgeMeeting");

        await().atMost(Duration.ofSeconds(10))
                .untilAsserted(() -> verify(acceptAcknowledgmentUseCase).acceptAcknowledgment("Met George"));
    }
}