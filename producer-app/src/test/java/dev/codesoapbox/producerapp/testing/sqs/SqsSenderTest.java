package dev.codesoapbox.producerapp.testing.sqs;

import dev.codesoapbox.producerapp.infrastructure.config.SqsBeanConfig;
import dev.codesoapbox.producerapp.acknowledgments.config.AcknowledgmentsSqsPublisherBeanConfig;
import dev.codesoapbox.producerapp.testing.containers.LocalStackContainerInitializer;
import io.awspring.cloud.test.sqs.SqsTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SqsTest
// For other queues, you might be able to use an embedded queue. Here, we use a LocalStack container for SQS:
@ContextConfiguration(initializers = LocalStackContainerInitializer.class)
@Import({SqsBeanConfig.class, AcknowledgmentsSqsPublisherBeanConfig.class})
@TestPropertySource(properties = {
        "producer-app.sqs-fail-if-not-found-queue-strategy.enabled=false"
})
@DirtiesContext // Otherwise, listeners in this context will steal messages from other integration tests
public @interface SqsSenderTest {
}
