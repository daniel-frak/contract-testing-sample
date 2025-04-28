package dev.codesoapbox.consumerapp.testing;

import dev.codesoapbox.consumerapp.greetings.config.ProducerAppSpringWebClientConfig;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@ExtendWith(SpringExtension.class) // Or @JsonTest, if you need that functionality
@ContextConfiguration(classes = ProducerAppSpringWebClientConfig.class)
@TestPropertySource(properties = "producer-app-url=http://localhost:8586")
@AutoConfigureStubRunner(ids = {"dev.codesoapbox:producer-app:+:stubs:8586"},
        stubsMode = StubRunnerProperties.StubsMode.LOCAL
        /* , mappingsOutputFolder = "target/outputmappings/" - If you want to debug your mappings */)
public @interface WebClientContractTest {
}
