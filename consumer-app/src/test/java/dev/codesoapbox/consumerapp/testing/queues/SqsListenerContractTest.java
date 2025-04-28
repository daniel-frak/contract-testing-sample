package dev.codesoapbox.consumerapp.testing.queues;

import dev.codesoapbox.consumerapp.testing.containers.LocalStackContainerInitializer;
import io.awspring.cloud.test.sqs.SqsTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import java.lang.annotation.*;

/**
 * Both normal and contract tests for queue Listeners should use this annotation, so that they share the Spring Boot
 * context and don't create duplicate instances of the same listeners (which would steal messages from each other).
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SqsTest
// For other queues, you might be able to use an embedded queue. Here, we use a LocalStack container for SQS:
@ContextConfiguration(initializers = LocalStackContainerInitializer.class)
@TestPropertySource(properties = {
        "consumer-app.sqs-fail-if-not-found-queue-strategy.enabled=false"
})
@ConfigureSharedSqsTestBeans
@Import(SqsMessageVerifierSender.class)
@AutoConfigureStubRunner(ids = {"dev.codesoapbox:producer-app:+:stubs:8585"},
        stubsMode = StubRunnerProperties.StubsMode.LOCAL)
public @interface SqsListenerContractTest {
}
