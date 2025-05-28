package ru.practicum.ewmservice.event.model;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.ewmservice.event.enums.Status;

import java.util.List;

@Getter
@Setter
public class EventRequestStatusUpdateRequest {


    private List<Long> requestIds;

    private Status status;
}
