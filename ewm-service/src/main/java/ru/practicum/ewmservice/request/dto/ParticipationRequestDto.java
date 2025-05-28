package ru.practicum.ewmservice.request.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.ewmservice.event.model.Status;

import java.time.LocalDateTime;

@Getter
@Setter
public class ParticipationRequestDto {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
    private LocalDateTime created;

    private Long event;

    private Long id;

    private Long requester;

    private Status status;
}