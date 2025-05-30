package ru.practicum.ewmservice.event.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LocationDto {

    @NotNull(message = "Широта не может быть null")
    private Float lat;

    @NotNull(message = "Долгота не может быть null")
    private Float lon;
}