package ru.practicum.ewmservice.compilation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.ewmservice.event.dto.EventShortDto;

import java.util.List;


@Getter
@Setter
public class CompilationDto {

    private Long id;

    private List<EventShortDto> events;

    @JsonProperty(defaultValue = "false")
    private Boolean pinned;

    private String title;
}
