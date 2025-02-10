package dev.codesoapbox.consumerapp.greetings.application;

import dev.codesoapbox.consumerapp.greetings.adapters.driven.http.ProducerAppWebClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetGreetingUseCase {

    private final ProducerAppWebClient producerAppWebClient;

    public String getGreetingMessage(String name) {
        return producerAppWebClient.getGreetingMessage(name);
    }
}
