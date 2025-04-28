package dev.codesoapbox.consumerapp.testing.containers;

import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.utility.DockerImageName;

/**
 * A wrapper for the {@link LocalStackContainer} which simplifies its setup by predefining the image version.
 */
public class PreconfiguredLocalStackContainer extends LocalStackContainer {

    private static final String IMAGE_VERSION = "localstack/localstack:4.3.0";

    public PreconfiguredLocalStackContainer() {
        super(getImageName());
    }

    private static DockerImageName getImageName() {
        return DockerImageName.parse(IMAGE_VERSION)
                .asCompatibleSubstituteFor("localstack/localstack");
    }
}
