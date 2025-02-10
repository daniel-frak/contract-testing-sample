package dev.codesoapbox.consumerapp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
        "consumer-app.sqs-fail-if-not-found-queue-strategy.enabled=false"
})
class MainAppApplicationIT {

    @Test
    void contextLoads() {
    }

}
