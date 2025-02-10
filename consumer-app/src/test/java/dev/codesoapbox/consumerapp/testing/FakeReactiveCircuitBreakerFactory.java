package dev.codesoapbox.consumerapp.testing;

import org.springframework.cloud.client.circuitbreaker.ConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * An implementation of {@link ReactiveCircuitBreakerFactory} for use in tests.
 * <p>
 * Provides helper methods for common testing tasks such as {@link #openCircuitBreaker}.
 * <p>
 * All circuit breakers start in a closed state.
 */
public class FakeReactiveCircuitBreakerFactory extends ReactiveCircuitBreakerFactory<Object, ConfigBuilder<Object>> {

    private final Map<String, TestCircuitBreaker> circuitBreakers = new HashMap<>();

    public void openCircuitBreaker(String id) {
        TestCircuitBreaker circuitBreaker = create(id);
        circuitBreaker.isOpen = true;
    }

    @Override
    public TestCircuitBreaker create(String id) {
        return circuitBreakers.computeIfAbsent(id, id2 -> new TestCircuitBreaker());
    }

    @Override
    protected ConfigBuilder<Object> configBuilder(String id) {
        return null;
    }

    @Override
    public void configureDefault(Function<String, Object> defaultConfiguration) {
        // Do nothing
    }

    public static class TestCircuitBreaker implements ReactiveCircuitBreaker {

        private static final RuntimeException CIRCUIT_BREAKER_OPEN_EXCEPTION =
                new RuntimeException("The circuit breaker is open and does not permit further calls");
        boolean isOpen;

        @Override
        public <T> Mono<T> run(Mono<T> toRun, Function<Throwable, Mono<T>> fallback) {
            if (isOpen) {
                return fallback.apply(CIRCUIT_BREAKER_OPEN_EXCEPTION);
            }
            return toRun;
        }

        @Override
        public <T> Flux<T> run(Flux<T> toRun, Function<Throwable, Flux<T>> fallback) {
            if (isOpen) {
                return fallback.apply(CIRCUIT_BREAKER_OPEN_EXCEPTION);
            }
            return toRun;
        }
    }
}
