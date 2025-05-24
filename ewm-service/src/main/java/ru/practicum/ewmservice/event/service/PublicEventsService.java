package ru.practicum.ewmservice.event.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.ewmservice.event.dto.EventFullDto;
import ru.practicum.ewmservice.event.dto.EventShortDto;

import java.util.List;

public interface PublicEventsService {

    List<EventShortDto> getEvents(String text, List<Integer> categories, Boolean paid, String rangeStart,
                                  String rangeEnd, Boolean onlyAvailable, String sort, Integer from, Integer size, HttpServletRequest request);

    EventFullDto getById(Integer id, HttpServletRequest request);
}
