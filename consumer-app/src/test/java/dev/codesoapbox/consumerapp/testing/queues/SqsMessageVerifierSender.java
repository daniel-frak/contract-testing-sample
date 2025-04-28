package dev.codesoapbox.consumerapp.testing.queues;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jetbrains.annotations.Nullable;
import org.springframework.cloud.contract.verifier.converter.YamlContract;
import org.springframework.cloud.contract.verifier.messaging.MessageVerifierSender;
import org.springframework.messaging.Message;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

import java.util.HashMap;
import java.util.Map;

/**
 * Required to verify queue messages against the contract.
 */
@RequiredArgsConstructor
public class SqsMessageVerifierSender implements MessageVerifierSender<Message<?>> {

    private final SqsTemplate sqsTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public void send(Message<?> message, String destination, @Nullable YamlContract contract) {
        sqsTemplate.send(destination, (String) message.getPayload());
    }

    @Override
    public <T> void send(T payload, Map<String, Object> headers, String destination, @Nullable YamlContract contract) {

        Object objectPayload = convertStringToMap(payload);
        sqsTemplate.send(destination, objectPayload);
    }

    /**
     * We want to use {@link SqsTemplate} instead of {@link SqsAsyncClient}
     * because it does a lot of things automatically (like appending message deduplication ids for FIFO queues).
     * Unfortunately, just passing a String to {@link SqsTemplate#send} will force the listener to receive the payload
     * as a simple String, instead of a JSON (and cause a mapping exception).
     * To get around this, we first convert the payload to a non-String object.
     */
    @SneakyThrows
    private <T> Object convertStringToMap(T payload) {
        TypeReference<HashMap<String, Object>> typeRef = new TypeReference<>() {
        };
        if (payload instanceof String) {
            return objectMapper.readValue((String) payload, typeRef);
        }
        return payload;
    }
}
