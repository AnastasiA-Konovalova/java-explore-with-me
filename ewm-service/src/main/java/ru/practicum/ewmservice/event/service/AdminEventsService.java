package ru.practicum.ewmservice.event.service;

import ru.practicum.ewmservice.event.dto.EventFullDto;
import ru.practicum.ewmservice.event.dto.FullEventDtoRequest;
import ru.practicum.ewmservice.event.model.UpdateEventAdminRequest;

import java.util.List;

public interface AdminEventsService {

    List<EventFullDto> getEventsByIds(FullEventDtoRequest fullEventDtoRequest, Long from, Long size);

    EventFullDto updateById(UpdateEventAdminRequest updateEvent, Long eventId);
}