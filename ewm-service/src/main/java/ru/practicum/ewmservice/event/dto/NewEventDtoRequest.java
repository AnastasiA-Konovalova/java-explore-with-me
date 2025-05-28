package ru.practicum.ewmservice.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.ewmservice.event.model.Location;

@Getter
@Setter
public class NewEventDtoRequest {

    @NotBlank
    @Size(min = 20, max = 2000, message = "Длина аннотации должна быть от 20 до 2000")
    private String annotation;

    @NotNull
    private Long category;

    @NotBlank
    @Size(min = 20, max = 7000, message = "Длина описания должна быть от 20 до 7000")
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String eventDate;

    @NotNull
    private Location location;

    @JsonProperty(defaultValue = "false")
    private Boolean paid;

    @Min(0)
    @JsonProperty(defaultValue = "0")
    private Long participantLimit;

    @JsonProperty(defaultValue = "true")
    private Boolean requestModeration;

    @Size(min = 3, max = 120, message = "Заголовок события должен быть от 3 до 120")
    private String title;
}