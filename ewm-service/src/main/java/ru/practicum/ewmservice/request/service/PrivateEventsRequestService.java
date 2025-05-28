package ru.practicum.ewmservice.request.service;

import ru.practicum.ewmservice.request.dto.ParticipationRequestDto;

import java.util.List;

public interface PrivateEventsRequestService {

    List<ParticipationRequestDto> getRequestForStrangerEvent(Long userId);

    ParticipationRequestDto saveEventRequest(Long userId, Long eventId);

    ParticipationRequestDto updateCancelRequest(Long userId, Long requestId);
}