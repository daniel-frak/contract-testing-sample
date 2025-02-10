package dev.codesoapbox.producerapp.greetings.adapters.driving.http.mappers;

import dev.codesoapbox.producerapp.greetings.adapters.driving.http.model.GreetResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface GreetResponseDtoMapper {

    @Mapping(target = "response", source = "response")
    GreetResponseDto toDto(String response);
}
