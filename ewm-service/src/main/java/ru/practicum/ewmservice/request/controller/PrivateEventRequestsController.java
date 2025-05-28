package ru.practicum.ewmservice.request.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewmservice.request.dto.ParticipationRequestDto;
import ru.practicum.ewmservice.request.service.PrivateEventsRequestService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users/{userId}/requests")
public class PrivateEventRequestsController {

    private final PrivateEventsRequestService eventsRequestService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ParticipationRequestDto> getRequestForStrangerEvent(@PathVariable Long userId) {
        return eventsRequestService.getRequestForStrangerEvent(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipationRequestDto saveEventRequest(@PathVariable Long userId,
                                                    @RequestParam Long eventId) {
        return eventsRequestService.saveEventRequest(userId, eventId);
    }

    @PatchMapping("/{requestId}/cancel")
    @ResponseStatus(HttpStatus.OK)
    public ParticipationRequestDto updateCancelRequest(@PathVariable Long userId,
                                                       @PathVariable Long requestId) {
        return eventsRequestService.updateCancelRequest(userId, requestId);
    }

}
