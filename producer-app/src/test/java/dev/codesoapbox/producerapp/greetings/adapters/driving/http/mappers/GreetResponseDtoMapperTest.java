package dev.codesoapbox.producerapp.greetings.adapters.driving.http.mappers;

import dev.codesoapbox.producerapp.greetings.adapters.driving.http.model.GreetResponseDto;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

class GreetResponseDtoMapperTest {

    private static final GreetResponseDtoMapper MAPPER = Mappers.getMapper(GreetResponseDtoMapper.class);

    @Test
    void shouldToDto() {
        String response = "A response";

        GreetResponseDto result = MAPPER.toDto(response);

        var expectedResult = new GreetResponseDto(response);
        assertThat(result).usingRecursiveComparison()
                .isEqualTo(expectedResult);
    }

    @Test
    void shouldReturnNullIfResponseIsNull() {
        GreetResponseDto result = MAPPER.toDto(null);

        assertThat(result).isNull();
    }
}