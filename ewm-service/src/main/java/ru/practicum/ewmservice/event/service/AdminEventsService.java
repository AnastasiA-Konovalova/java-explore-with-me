package ru.practicum.ewmservice.event.service;

import ru.practicum.ewmservice.event.dto.EventFullDto;
import ru.practicum.ewmservice.event.model.UpdateEventAdminRequest;

import java.util.List;

public interface AdminEventsService {

    List<EventFullDto> getEventsByIds(List<Long> users, List<String> states, List<Long> categories,
                                      String rangeStart, String rangeEnd, Long from, Long size);

    EventFullDto updateById(UpdateEventAdminRequest updateEvent, Long eventId);
}