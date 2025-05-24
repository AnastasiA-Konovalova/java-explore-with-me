package ru.practicum.ewmservice.event.service;

import ru.practicum.ewmservice.event.dto.EventFullDto;
import ru.practicum.ewmservice.event.dto.EventRequestStatusUpdateRequest;
import ru.practicum.ewmservice.event.dto.EventShortDto;
import ru.practicum.ewmservice.event.dto.NewEventDtoRequest;
import ru.practicum.ewmservice.event.dto.ParticipationRequestDto;
import ru.practicum.ewmservice.event.dto.UpdateEventUserRequest;

import java.util.List;

public interface PrivateEventsService {

    EventFullDto saveEvent(NewEventDtoRequest eventDtoRequest, Integer userId);

    EventFullDto updateEvent(UpdateEventUserRequest updateEvent, Integer userId, Integer eventId);

    EventFullDto updateEventRequests(EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest,
                                     Integer userId, Integer eventId);

    List<EventShortDto> getEventByUserId(Integer userId, Integer from, Integer size);

    EventFullDto getFullEventInfoByUserId(Integer userId, Integer eventId);

    ParticipationRequestDto getRequestForEventById(Integer userId, Integer eventId);

}
