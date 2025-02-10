package dev.codesoapbox.consumerapp.greetings.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ProducerAppSpringWebClientConfig {

    public static final String PRODUCER_APP_SPRING_WEB_CLIENT_QUALIFIER = "producerAppSpringWebClient";

    @Bean(name = PRODUCER_APP_SPRING_WEB_CLIENT_QUALIFIER)
    public WebClient producerAppSpringWebClient(
            @Value("${producer-app-url}") String producerAppUrl
    ) {
        return WebClient.builder()
                .baseUrl(producerAppUrl)
                .build();
    }
}
