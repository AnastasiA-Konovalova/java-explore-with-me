package ru.practicum.ewmservice.event.service;

import ru.practicum.ewmservice.event.dto.EventFullDto;
import ru.practicum.ewmservice.event.dto.EventShortDto;
import ru.practicum.ewmservice.event.dto.NewEventDtoRequest;
import ru.practicum.ewmservice.event.model.Event;
import ru.practicum.ewmservice.event.model.EventRequestStatusUpdateRequest;
import ru.practicum.ewmservice.event.model.EventRequestStatusUpdateResult;
import ru.practicum.ewmservice.event.model.UpdateEventUserRequest;
import ru.practicum.ewmservice.request.dto.ParticipationRequestDto;

import java.util.List;

public interface PrivateEventsService {

    EventFullDto saveEvent(NewEventDtoRequest eventDtoRequest, Long userId);

    EventFullDto updateEvent(UpdateEventUserRequest updateEvent, Long userId, Long eventId);

    EventRequestStatusUpdateResult updateEventRequests(EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest,
                                                       Long userId, Long eventId);

    List<EventShortDto> getEventByUserId(Long userId, Long from, Long size);

    EventFullDto getFullEventInfoByUserId(Long userId, Long eventId);

    List<ParticipationRequestDto> getRequestForEventById(Long userId, Long eventId);

    Event getById(Long eventId);
}