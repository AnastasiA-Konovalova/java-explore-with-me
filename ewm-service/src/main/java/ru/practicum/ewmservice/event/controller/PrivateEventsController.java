package ru.practicum.ewmservice.event.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewmservice.event.dto.EventFullDto;
import ru.practicum.ewmservice.event.dto.EventShortDto;
import ru.practicum.ewmservice.event.dto.NewEventDtoRequest;
import ru.practicum.ewmservice.event.model.EventRequestStatusUpdateRequest;
import ru.practicum.ewmservice.event.model.EventRequestStatusUpdateResult;
import ru.practicum.ewmservice.event.model.UpdateEventUserRequest;
import ru.practicum.ewmservice.event.service.PrivateEventsService;
import ru.practicum.ewmservice.request.dto.ParticipationRequestDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users/{userId}/events")
public class PrivateEventsController {

    private final PrivateEventsService eventsService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto saveEvent(@Valid @RequestBody NewEventDtoRequest eventDtoRequest,
                                  @PathVariable Long userId) {
        return eventsService.saveEvent(eventDtoRequest, userId);
    }

    @PatchMapping("{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto updateEvent(@Valid @RequestBody UpdateEventUserRequest updateEvent,
                                    @PathVariable Long userId,
                                    @PathVariable(name = "eventId") Long eventId) {
        return eventsService.updateEvent(updateEvent, userId, eventId);
    }

    @PatchMapping("{eventId}/requests")
    @ResponseStatus(HttpStatus.OK)
    public EventRequestStatusUpdateResult updateEventRequests(@RequestBody EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest,
                                                              @PathVariable Long userId,
                                                              @PathVariable(name = "eventId") Long eventId) {
        return eventsService.updateEventRequests(eventRequestStatusUpdateRequest, userId, eventId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EventShortDto> getEventByUserId(@PathVariable Long userId,
                                                @RequestParam(defaultValue = "0") Long from,
                                                @RequestParam(defaultValue = "10") Long size) {
        return eventsService.getEventByUserId(userId, from, size);
    }

    @GetMapping("{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto getFullEventInfoByUserId(@PathVariable Long userId,
                                                 @PathVariable(name = "eventId") Long eventId) {
        return eventsService.getFullEventInfoByUserId(userId, eventId);
    }

    @GetMapping("{eventId}/requests")
    @ResponseStatus(HttpStatus.OK)
    public List<ParticipationRequestDto> getRequestForEventById(@PathVariable Long userId,
                                                                @PathVariable(name = "eventId") Long eventId) {
        return eventsService.getRequestForEventById(userId, eventId);
    }
}