package dev.codesoapbox.producerapp.greetings.adapters.driving.http.model;

import jakarta.validation.constraints.NotBlank;

public record GreetResponseDto(
        @NotBlank String response
) {
}
