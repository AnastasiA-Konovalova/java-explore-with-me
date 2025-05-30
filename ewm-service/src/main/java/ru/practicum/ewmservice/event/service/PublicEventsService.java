package ru.practicum.ewmservice.event.service;

import jakarta.servlet.http.HttpServletRequest;
import ru.practicum.ewmservice.event.dto.EventFullDto;
import ru.practicum.ewmservice.event.dto.EventShortDto;
import ru.practicum.ewmservice.event.dto.ShortEventDtoRequest;

import java.util.List;

public interface PublicEventsService {

    List<EventShortDto> getEvents(ShortEventDtoRequest eventDtoRequest, Long from, Long size, HttpServletRequest request);

    EventFullDto getById(Long id, HttpServletRequest request);
}