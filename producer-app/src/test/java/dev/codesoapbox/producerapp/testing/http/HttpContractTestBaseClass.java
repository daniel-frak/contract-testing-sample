package dev.codesoapbox.producerapp.testing.http;

import dev.codesoapbox.producerapp.greetings.application.GetGreetingUseCase;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;

import java.time.Clock;

import static org.mockito.Mockito.lenient;

@ControllerTest
public class HttpContractTestBaseClass {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private Clock clock;

    @Autowired
    private GetGreetingUseCase getGreetingUseCase;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);

        mockTestUseCase();
        // ... More use case mocking here
    }

    private void mockTestUseCase() {
        // Must be lenient because setup is the same for every contract test
        lenient().when(getGreetingUseCase.getGreetingMessage(org.mockito.ArgumentMatchers.anyString()))
                // Injecting the clock here is not strictly necessary -
                // could either reference TestTimeBeanConfig.FIXED_DATE_TIME or hardcode the value in the String
                .thenAnswer(invocation -> {
                    String name = invocation.getArgument(0);
                    return "Hello, " + name + " (from test producer at " + clock.instant() + ")!";
                });
    }
}
