package dev.codesoapbox.consumerapp.greetings.adapters.driven.http;

import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class ProducerAppWebClient {

    protected static final String GREETING_CIRCUIT_BREAKER_ID = "greeting-circuit-breaker";
    private static final String SECRET_CODE = "123456";

    private final WebClient springWebClient;
    private final ReactiveCircuitBreaker testCircuitBreaker;

    public ProducerAppWebClient(WebClient springWebClient, ReactiveCircuitBreakerFactory<?, ?> circuitBreakerFactory) {
        this.springWebClient = springWebClient;
        testCircuitBreaker = circuitBreakerFactory.create(GREETING_CIRCUIT_BREAKER_ID);
    }

    public String getGreetingMessage(String name) {
        var requestBody = new TestDto(name, SECRET_CODE);

        Mono<TestResponseDto> responseMono = springWebClient.post()
                .uri("greet")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .bodyToMono(TestResponseDto.class);

        TestResponseDto responseDto = testCircuitBreaker.run(responseMono,
                        throwable -> Mono.fromSupplier(
                                () -> new TestResponseDto("Could not retrieve response")))
                .block();
        return responseDto.response();
    }

    public record TestDto(
            String consumerName,
            String secretCode
    ) {
    }

    public record TestResponseDto(
            String response
    ) {
    }
}
