package ru.practicum.ewmservice.event.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewmservice.event.dto.EventFullDto;
import ru.practicum.ewmservice.event.dto.FullEventDtoRequest;
import ru.practicum.ewmservice.event.model.UpdateEventAdminRequest;
import ru.practicum.ewmservice.event.service.AdminEventsService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/events")
public class AdminEventsController {

    private final AdminEventsService eventsService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EventFullDto> getEventsByIds(@Valid @ModelAttribute FullEventDtoRequest fullEventDtoRequest,
                                             @RequestParam(defaultValue = "0") Long from,
                                             @RequestParam(defaultValue = "10") Long size) {
        return eventsService.getEventsByIds(fullEventDtoRequest, from, size);
    }

    @PatchMapping("/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto updateById(@Valid @RequestBody UpdateEventAdminRequest updateEvent,
                                   @PathVariable(name = "eventId") Long eventId) {
        return eventsService.updateById(updateEvent, eventId);
    }
}