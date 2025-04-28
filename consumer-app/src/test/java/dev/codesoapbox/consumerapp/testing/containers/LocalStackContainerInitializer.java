package dev.codesoapbox.consumerapp.testing.containers;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.localstack.LocalStackContainer;

/**
 * {@link ApplicationContextInitializer} that configures a reusable LocalStack container for integration testing.
 * <p>
 * The Spring environment is automatically configured to use the container.
 * <p>
 * Should typically be used as {@code @ContextConfiguration(initializers = LocalStackContainerInitializer.class)}
 * on a slice test annotation.
 * <p>
 * Based on:
 * <a href="https://stackoverflow.com/a/68890310">https://stackoverflow.com/a/68890310</a>
 *
 * @see ApplicationContextInitializer
 * @see PreconfiguredLocalStackContainer
 */
public class LocalStackContainerInitializer
        implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static final PreconfiguredLocalStackContainer CONTAINER = new PreconfiguredLocalStackContainer();
    private static final String SQS_ENDPOINT = "spring.cloud.aws.sqs.endpoint";
    private static final String AWS_REGION = "spring.cloud.aws.region.static";

    public LocalStackContainerInitializer() {
        CONTAINER.withServices(LocalStackContainer.Service.SQS)
                .start();
    }

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        TestPropertyValues.of(
                SQS_ENDPOINT + "=" + CONTAINER.getEndpointOverride(LocalStackContainer.Service.SQS),
                AWS_REGION + "=" + CONTAINER.getRegion()
        ).applyTo(applicationContext.getEnvironment());
    }
}
