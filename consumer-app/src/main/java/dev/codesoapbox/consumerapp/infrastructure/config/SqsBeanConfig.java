package dev.codesoapbox.consumerapp.infrastructure.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.sqs.config.SqsMessageListenerContainerFactory;
import io.awspring.cloud.sqs.listener.QueueNotFoundStrategy;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

@Configuration
public class SqsBeanConfig {

    private static final String IS_SQS_FAIL_STRATEGY_VALUE =
            "${consumer-app.sqs-fail-if-not-found-queue-strategy.enabled}";

    @Bean
    SqsMessageListenerContainerFactory<Object> defaultSqsListenerContainerFactory(
            SqsAsyncClient sqsAsyncClient,
            @Value(IS_SQS_FAIL_STRATEGY_VALUE) boolean isSqsFailStrategy) {
        return SqsMessageListenerContainerFactory
                .builder()
                .sqsAsyncClient(sqsAsyncClient)
                .configure(options -> options.queueNotFoundStrategy(
                        getQueueNotFoundStrategy(isSqsFailStrategy)))
                .build();
    }

    private QueueNotFoundStrategy getQueueNotFoundStrategy(boolean isSqsFailStrategy) {
        return isSqsFailStrategy ? QueueNotFoundStrategy.FAIL : QueueNotFoundStrategy.CREATE;
    }

    @Bean
    public SqsTemplate sqsTemplate(
            SqsAsyncClient sqsAsyncClient,
            ObjectMapper objectMapper,
            @Value(IS_SQS_FAIL_STRATEGY_VALUE) boolean isSqsFailStrategy) {
        return SqsTemplate.builder().sqsAsyncClient(sqsAsyncClient)
                .configureDefaultConverter(converter -> {
                    // Use Spring Boot's ObjectMapper
                    converter.setObjectMapper(objectMapper);
                    // Don't include JavaType as header:
                    converter.setPayloadTypeHeaderValueFunction(msg -> null);
                })
                .configure(options -> options
                        .queueNotFoundStrategy(getQueueNotFoundStrategy(isSqsFailStrategy)))
                .build();
    }
}
