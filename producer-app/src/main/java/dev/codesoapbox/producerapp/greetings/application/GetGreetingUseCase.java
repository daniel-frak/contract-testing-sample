package dev.codesoapbox.producerapp.greetings.application;

import dev.codesoapbox.producerapp.acknowledgments.application.MeetingAcknowledgmentPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Clock;

@Service
@RequiredArgsConstructor
public class GetGreetingUseCase {

    private final Clock clock;
    private final MeetingAcknowledgmentPublisher meetingAcknowledgmentPublisher;

    public String getGreetingMessage(String name) {
        meetingAcknowledgmentPublisher.publishMeetingAcknowledgment(name);
        return "Hello, " + name + " (from production producer at " + clock.instant() + ")!";
    }
}
