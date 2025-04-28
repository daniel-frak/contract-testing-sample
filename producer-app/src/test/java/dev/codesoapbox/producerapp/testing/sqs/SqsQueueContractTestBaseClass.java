package dev.codesoapbox.producerapp.testing.sqs;

import dev.codesoapbox.producerapp.acknowledgments.adapters.driven.sqs.MeetingAcknowledgmentSqsPublisher;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.context.annotation.Import;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

@SqsSenderTest
@Import(SqsQueueMessageVerifierReceiver.class)
@AutoConfigureMessageVerifier
public class SqsQueueContractTestBaseClass {

    @Autowired
    private SqsAsyncClient sqsAsyncClient;

    @Autowired
    private MeetingAcknowledgmentSqsPublisher meetingAcknowledgmentSqsPublisher;

    @AfterEach
    void tearDown() {
        purgeAllQueues();
    }

    private void purgeAllQueues() {
        for (String queueUrl : sqsAsyncClient.listQueues().join().queueUrls()) {
            sqsAsyncClient.purgeQueue(request -> request.queueUrl(queueUrl)).join();
        }
    }

    /**
     * This method is called by the contract test based on the contract file.
     * It sends a reminder message to the "meeting-acknowledgments" queue.
     */
    public void acknowledgeMeeting() {
        meetingAcknowledgmentSqsPublisher.publishMeetingAcknowledgment("George");
    }
}
