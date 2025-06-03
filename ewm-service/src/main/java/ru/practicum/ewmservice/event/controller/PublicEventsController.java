package ru.practicum.ewmservice.event.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewmservice.event.dto.EventFullDto;
import ru.practicum.ewmservice.event.dto.EventShortDto;
import ru.practicum.ewmservice.event.dto.ShortEventDtoRequest;
import ru.practicum.ewmservice.event.service.PublicEventsService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/events")
public class PublicEventsController {

    private final PublicEventsService eventsService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EventShortDto> getEvents(@Valid @ModelAttribute ShortEventDtoRequest eventDtoRequest,
                                         @RequestParam(defaultValue = "0") Long from,
                                         @RequestParam(defaultValue = "10") Long size,
                                         HttpServletRequest request) {
        return eventsService.getEvents(eventDtoRequest, from, size, request);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto getById(@PathVariable(name = "id") Long id, HttpServletRequest request) {
        return eventsService.getById(id, request);
    }
}