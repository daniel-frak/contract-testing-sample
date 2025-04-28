package dev.codesoapbox.consumerapp.greetings.adapters.driven.http;

import dev.codesoapbox.consumerapp.greetings.config.ProducerAppSpringWebClientConfig;
import dev.codesoapbox.consumerapp.testing.FakeReactiveCircuitBreakerFactory;
import dev.codesoapbox.consumerapp.testing.WebClientContractTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.reactive.function.client.WebClient;

import static org.assertj.core.api.Assertions.assertThat;

@WebClientContractTest
class ProducerAppWebClientIT {

    private ProducerAppWebClient webClient;

    @Autowired
    @Qualifier(ProducerAppSpringWebClientConfig.PRODUCER_APP_SPRING_WEB_CLIENT_QUALIFIER)
    private WebClient producerSpringWebClient;

    private FakeReactiveCircuitBreakerFactory reactiveCircuitBreakerFactory;

    @BeforeEach
    void setUp() {
        reactiveCircuitBreakerFactory = new FakeReactiveCircuitBreakerFactory();
        webClient = new ProducerAppWebClient(producerSpringWebClient, reactiveCircuitBreakerFactory);
    }

    @Test
    void shouldGetGreetingMessage() {
        String result = webClient.getGreetingMessage("Consumer");

        assertThat(result).isEqualTo("Hello, Consumer (from test producer at 2025-01-01T00:00:00Z)!");
    }

    @Test
    void testShouldReturnFallbackValueIfCircuitBreakerIsOpen() {
        reactiveCircuitBreakerFactory.openCircuitBreaker(ProducerAppWebClient.GREETING_CIRCUIT_BREAKER_ID);

        String result = webClient.getGreetingMessage("Consumer");
        assertThat(result).isEqualTo("Could not retrieve response");
    }
}