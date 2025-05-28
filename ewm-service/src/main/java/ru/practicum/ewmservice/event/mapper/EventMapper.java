package ru.practicum.ewmservice.event.mapper;

import ru.practicum.ewmservice.category.mapper.CategoryMapper;
import ru.practicum.ewmservice.category.model.Category;
import ru.practicum.ewmservice.event.dto.EventFullDto;
import ru.practicum.ewmservice.event.dto.EventShortDto;
import ru.practicum.ewmservice.event.dto.NewEventDtoRequest;
import ru.practicum.ewmservice.event.model.Event;
import ru.practicum.ewmservice.event.model.Location;
import ru.practicum.ewmservice.user.mapper.UserMapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EventMapper {

    public static EventShortDto toShortDto(Event event) {
        EventShortDto eventShortDto = new EventShortDto();
        eventShortDto.setId(event.getId());
        eventShortDto.setAnnotation(event.getAnnotation());
        eventShortDto.setCategory(CategoryMapper.toDto(event.getCategory()));
        eventShortDto.setPaid(event.getPaid());
        eventShortDto.setTitle(event.getTitle());
        eventShortDto.setConfirmedRequests(event.getConfirmedRequests());
        eventShortDto.setEventDate(event.getEventDate());
        eventShortDto.setInitiator(UserMapper.toShortDto(event.getInitiator()));
        eventShortDto.setViews(event.getViews());

        return eventShortDto;
    }

    public static EventFullDto toFullDto(Event event) {
        EventFullDto eventFullDto = new EventFullDto();
        eventFullDto.setId(event.getId());
        eventFullDto.setAnnotation(event.getAnnotation());
        eventFullDto.setCategory(CategoryMapper.toDto(event.getCategory()));
        eventFullDto.setDescription(event.getDescription());
        eventFullDto.setConfirmedRequests(event.getConfirmedRequests());
        eventFullDto.setCreatedOn(event.getCreatedOn());
        eventFullDto.setEventDate(event.getEventDate());
        eventFullDto.setInitiator(UserMapper.toShortDto(event.getInitiator()));
        eventFullDto.setLocation(event.getLocation());
        eventFullDto.setPaid(event.getPaid());
        eventFullDto.setParticipantLimit(event.getParticipantLimit());
        eventFullDto.setPublishedOn(event.getPublishedOn());
        eventFullDto.setRequestModeration(event.getRequestModeration());
        eventFullDto.setState(event.getState());
        eventFullDto.setTitle(event.getTitle());
        eventFullDto.setViews(event.getViews());

        return eventFullDto;
    }

    public static NewEventDtoRequest toNewEventDto(Event event) {
        NewEventDtoRequest newEventDto = new NewEventDtoRequest();
        newEventDto.setAnnotation(event.getAnnotation());
        newEventDto.setCategory(event.getCategory().getId());
        newEventDto.setDescription(event.getDescription());
        newEventDto.setEventDate(String.valueOf(event.getEventDate()));
        newEventDto.setLocation(event.getLocation());
        newEventDto.setPaid(event.getPaid());
        newEventDto.setParticipantLimit(event.getParticipantLimit());
        newEventDto.setRequestModeration(event.getRequestModeration());
        newEventDto.setTitle(event.getTitle());

        return newEventDto;

    }

    public static Event toEntityForShort(EventShortDto eventShortDto) {
        Event event = new Event();
        event.setId(eventShortDto.getId());
        event.setAnnotation(eventShortDto.getAnnotation());
        event.setCategory(CategoryMapper.toEntityCategory(eventShortDto.getCategory()));
        event.setPaid(eventShortDto.getPaid());
        event.setTitle(eventShortDto.getTitle());
        event.setConfirmedRequests(eventShortDto.getConfirmedRequests());
        event.setEventDate(eventShortDto.getEventDate());
        event.setInitiator(UserMapper.toShortEntity(eventShortDto.getInitiator()));
        event.setViews(event.getViews());

        return event;
    }

    public static Event toEntityForFull(EventFullDto eventFullDto) {
        Event event = new Event();
        event.setId(eventFullDto.getId());
        event.setAnnotation(eventFullDto.getAnnotation());
        event.setCategory(CategoryMapper.toEntityCategory(eventFullDto.getCategory()));
        event.setConfirmedRequests(eventFullDto.getConfirmedRequests());
        event.setCreatedOn(eventFullDto.getCreatedOn());
        event.setEventDate(eventFullDto.getEventDate());
        event.setInitiator(UserMapper.toShortEntity(eventFullDto.getInitiator()));
        event.setLocation(eventFullDto.getLocation());
        event.setPaid(eventFullDto.getPaid());
        event.setParticipantLimit(eventFullDto.getParticipantLimit());
        event.setPublishedOn(eventFullDto.getPublishedOn());
        event.setRequestModeration(eventFullDto.getRequestModeration());
        event.setState(eventFullDto.getState());
        event.setTitle(eventFullDto.getTitle());
        event.setViews(eventFullDto.getViews());

        return event;
    }

    public static Event toEntityForNewEvent(NewEventDtoRequest newEventDto, Category category, Long userId, Location location) {
        Event event = new Event();
        event.setAnnotation(newEventDto.getAnnotation());
        event.setCategory(category);
        event.setDescription(newEventDto.getDescription());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        event.setEventDate(LocalDateTime.parse(newEventDto.getEventDate(), formatter));
        event.setLocation(location);
        if (Boolean.TRUE.equals(newEventDto.getPaid())) {
            event.setPaid(true);
        } else {
            event.setPaid(false);
        }

        if (newEventDto.getParticipantLimit() != null) {
            event.setParticipantLimit(newEventDto.getParticipantLimit());
        } else {
            event.setParticipantLimit(0L);
        }
        if (newEventDto.getRequestModeration() == null || newEventDto.getRequestModeration()) {
            event.setRequestModeration(true);
        } else {
            event.setRequestModeration(false);
        }
        event.setTitle(newEventDto.getTitle());

        return event;
    }
}
