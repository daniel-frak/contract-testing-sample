package dev.codesoapbox.producerapp.testing.sqs;

import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jetbrains.annotations.Nullable;
import org.springframework.cloud.contract.verifier.converter.YamlContract;
import org.springframework.cloud.contract.verifier.messaging.MessageVerifierReceiver;

import java.util.*;
import java.util.concurrent.TimeUnit;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.support.MessageBuilder;

import static java.util.Collections.*;
import static java.util.Collections.emptySet;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;

/**
 * Required to verify queue messages against the contract.
 */
@RequiredArgsConstructor
public class SqsQueueMessageVerifierReceiver implements MessageVerifierReceiver<Message<?>> {

    private final Map<String, Set<Message<?>>> consumedEventsByQueueName = synchronizedMap(new HashMap<>());

    @SqsListener(queueNames = {
            // Unfortunately, we need to manually subscribe to every queue here
            "meeting-acknowledgments"
    })
    public void listen(String payload, @Header("Sqs_QueueName") String queueName) {
        addMessage(payload, queueName);
    }

    private void addMessage(String payload, String queueName) {
        consumedEventsByQueueName.computeIfAbsent(queueName, key -> synchronizedSet(new HashSet<>()))
                .add(MessageBuilder.createMessage(payload, new MessageHeaders(emptyMap())));
    }

    @Override
    public Message<?> receive(String destination, YamlContract contract) {
        return receive(destination, 5, SECONDS, contract);
    }

    @SneakyThrows
    @Override
    public Message<?> receive(String destination, long timeout, TimeUnit timeUnit, @Nullable YamlContract contract) {
        await().atMost(timeout, timeUnit)
                .until(() -> !consumedEventsByQueueName.isEmpty());

        return getFirstMessage(destination);
    }

    private Message<?> getFirstMessage(String destination) {
        return consumedEventsByQueueName.getOrDefault(destination, emptySet()).stream()
                .findFirst()
                .orElse(null);
    }
}
