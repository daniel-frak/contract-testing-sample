package dev.codesoapbox.consumerapp.greetings.adapters.driving.http;

import dev.codesoapbox.consumerapp.greetings.application.GetGreetingUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class GreetController {

    private final GetGreetingUseCase useCase;

    @GetMapping("greet")
    public String greet(@RequestParam("name") String name) {
        return useCase.getGreetingMessage(name);
    }
}
