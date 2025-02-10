package dev.codesoapbox.consumerapp.greetings.config;

import dev.codesoapbox.consumerapp.greetings.adapters.driven.http.ProducerAppWebClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ProducerAppWebClientConfig {

    @Bean
    public ProducerAppWebClient producerAppWebClient(
            @Qualifier(ProducerAppSpringWebClientConfig.PRODUCER_APP_SPRING_WEB_CLIENT_QUALIFIER)
            WebClient springWebClient,
            ReactiveCircuitBreakerFactory<?, ?> circuitBreakerFactory
    ) {
        return new ProducerAppWebClient(springWebClient, circuitBreakerFactory);
    }
}
