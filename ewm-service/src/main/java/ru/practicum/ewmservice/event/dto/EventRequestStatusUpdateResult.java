package ru.practicum.ewmservice.event.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventRequestStatusUpdateResult {

    private ParticipationRequestDto confirmedRequests;

    private ParticipationRequestDto rejectedRequests;
}
