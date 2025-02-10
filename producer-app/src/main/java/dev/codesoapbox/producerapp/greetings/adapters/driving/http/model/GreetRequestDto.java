package dev.codesoapbox.producerapp.greetings.adapters.driving.http.model;

import jakarta.validation.constraints.NotBlank;

public record GreetRequestDto(
        @NotBlank String consumerName,
        @NotBlank String secretCode
) {
}
