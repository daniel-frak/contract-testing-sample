package dev.codesoapbox.consumerapp.greetings.adapters.driving.http;

import dev.codesoapbox.consumerapp.greetings.application.GetGreetingUseCase;
import dev.codesoapbox.consumerapp.testing.http.ControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ControllerTest
class GreetControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GetGreetingUseCase useCase;

    @Test
    void shouldGreet() throws Exception {
        String expectedResponse = "A response";

        when(useCase.getGreetingMessage("World"))
                .thenReturn(expectedResponse);

        mockMvc.perform(get("/greet?name=World"))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResponse));
    }
}