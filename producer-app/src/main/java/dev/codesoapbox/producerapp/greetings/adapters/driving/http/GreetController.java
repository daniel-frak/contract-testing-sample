package dev.codesoapbox.producerapp.greetings.adapters.driving.http;

import dev.codesoapbox.producerapp.greetings.application.GetGreetingUseCase;
import dev.codesoapbox.producerapp.greetings.adapters.driving.http.mappers.GreetResponseDtoMapper;
import dev.codesoapbox.producerapp.greetings.adapters.driving.http.model.GreetRequestDto;
import dev.codesoapbox.producerapp.greetings.adapters.driving.http.model.GreetResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("greet")
public class GreetController {

    private final GetGreetingUseCase getGreetingUseCase;
    private final GreetResponseDtoMapper greetResponseDtoMapper;

    @PostMapping
    public GreetResponseDto greet(@Valid @RequestBody GreetRequestDto body) {
        String message = getGreetingUseCase.getGreetingMessage(body.consumerName());
        return greetResponseDtoMapper.toDto(message);
    }
}
