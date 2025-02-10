package dev.codesoapbox.consumerapp.acknowledgments.application;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AcceptAcknowledgmentUseCase {

    public void acceptAcknowledgment(String message) {
        log.info("Received meeting acknowledgment: {}", message);
    }
}
