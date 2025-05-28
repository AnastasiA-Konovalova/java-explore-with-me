package ru.practicum.ewmservice.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.ewmservice.category.dto.CategoryDto;
import ru.practicum.ewmservice.event.model.Location;
import ru.practicum.ewmservice.event.model.State;
import ru.practicum.ewmservice.event.model.StateAction;
import ru.practicum.ewmservice.user.dto.UserShortDto;

@Getter
@Setter
public class UpdateEventUserRequest {

    private Long id;

    @Size(min = 20, max = 2000)
    private String annotation;

    private CategoryDto category;

    @Size(min = 20, max = 7000)
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String eventDate;

    private Location location;

    private Boolean paid;

    @Positive
    private Long participantLimit;

    private Boolean requestModeration;

    private StateAction stateAction;

    @Size(min = 3, max = 120)
    private String title;
}
