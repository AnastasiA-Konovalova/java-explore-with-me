package ru.practicum.ewmservice.event.dto;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.ewmservice.event.model.Status;

import java.util.List;

@Getter
@Setter
public class EventRequestStatusUpdateRequest {

    private List<Integer> requestIds;

    private Status status;
}
