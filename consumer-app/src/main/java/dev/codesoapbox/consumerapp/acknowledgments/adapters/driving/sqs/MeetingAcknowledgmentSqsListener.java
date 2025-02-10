package dev.codesoapbox.consumerapp.acknowledgments.adapters.driving.sqs;

import dev.codesoapbox.consumerapp.acknowledgments.application.AcceptAcknowledgmentUseCase;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class MeetingAcknowledgmentSqsListener {

    protected static final String ACKNOWLEDGMENTS_QUEUE = "meeting-acknowledgments";

    private final AcceptAcknowledgmentUseCase acceptAcknowledgmentUseCase;

    @SqsListener(queueNames = ACKNOWLEDGMENTS_QUEUE)
    public void listen(MeetingAcknowledgmentSqsDto payload) {
        acceptAcknowledgmentUseCase.acceptAcknowledgment(payload.message());
    }
}
