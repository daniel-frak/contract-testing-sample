package dev.codesoapbox.consumerapp.greetings.adapters.driven.http;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import dev.codesoapbox.consumerapp.testing.FakeReactiveCircuitBreakerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import static org.assertj.core.api.Assertions.assertThat;

@WireMockTest(httpPort = 8081)
class ProducerAppWebClientIT {

    private ProducerAppWebClient webClient;
    private FakeReactiveCircuitBreakerFactory reactiveCircuitBreakerFactory;

    @BeforeEach
    void setUp() {
        reactiveCircuitBreakerFactory = new FakeReactiveCircuitBreakerFactory();
        WebClient producerSpringWebClient = WebClient.builder()
                .baseUrl("http://localhost:8081")
                .build();
        webClient = new ProducerAppWebClient(producerSpringWebClient, reactiveCircuitBreakerFactory);
    }

    @Test
    void testShouldReturnFallbackValueIfCircuitBreakerIsOpen() {
        reactiveCircuitBreakerFactory.openCircuitBreaker(ProducerAppWebClient.GREETING_CIRCUIT_BREAKER_ID);

        String result = webClient.getGreetingMessage("Consumer");
        assertThat(result).isEqualTo("Could not retrieve response");
    }
}