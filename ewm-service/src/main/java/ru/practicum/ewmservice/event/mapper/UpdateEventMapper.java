package ru.practicum.ewmservice.event.mapper;

import ru.practicum.ewmservice.category.model.Category;
import ru.practicum.ewmservice.event.dto.UpdateEventUserRequest;
import ru.practicum.ewmservice.event.model.Event;
import ru.practicum.ewmservice.event.model.Location;
import ru.practicum.ewmservice.event.model.State;
import ru.practicum.ewmservice.event.model.UpdateEventAdminRequest;
import ru.practicum.ewmservice.exception.ConflictException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UpdateEventMapper {

    public static Event toEvent(UpdateEventUserRequest updateEvent, Event event, Category category, Location location) {
        if (updateEvent.getAnnotation() != null) {
            event.setAnnotation(updateEvent.getAnnotation());
        }

        if (updateEvent.getCategory() != null) {
            event.setCategory(category);
        }

        if (updateEvent.getDescription() != null) {
            event.setDescription(updateEvent.getDescription());
        }

        if (updateEvent.getEventDate() != null) {
            event.setEventDate(LocalDateTime.parse(updateEvent.getEventDate()));
        }

        if (updateEvent.getLocation() != null) {
            event.setLocation(location);
        }

        if (updateEvent.getPaid() != null) {
            event.setPaid(updateEvent.getPaid());
        }

        if (updateEvent.getParticipantLimit() != null) {
            event.setParticipantLimit(updateEvent.getParticipantLimit());
        }

        if (updateEvent.getRequestModeration() != null) {
            event.setRequestModeration(updateEvent.getRequestModeration());
        }

        if (updateEvent.getTitle() != null) {
            event.setTitle(updateEvent.getTitle());
        }

        if (updateEvent.getStateAction() != null) {
            switch (updateEvent.getStateAction()) {
                case SEND_TO_REVIEW -> event.setState(State.PENDING);
                case CANCEL_REVIEW -> event.setState(State.CANCELED);
            }
        }
        return event;
    }

    public static Event toEventUpdateAdmin(UpdateEventAdminRequest updateEvent, Event event, Category category, Location location) {
        if (updateEvent.getAnnotation() != null) {
            event.setAnnotation(updateEvent.getAnnotation());
        }

        if (updateEvent.getCategory() != null) {
            event.setCategory(category);
        }

        if (updateEvent.getDescription() != null) {
            event.setDescription(updateEvent.getDescription());
        }

        if (updateEvent.getEventDate() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            event.setEventDate(LocalDateTime.parse(updateEvent.getEventDate(), formatter));
        }

        if (updateEvent.getLocation() != null) {
            event.setLocation(location);
        }

        if (updateEvent.getPaid() != null) {
            event.setPaid(updateEvent.getPaid());
        }

        if (updateEvent.getParticipantLimit() != null) {
            event.setParticipantLimit(updateEvent.getParticipantLimit());
        }

        if (updateEvent.getRequestModeration() != null) {
            event.setRequestModeration(updateEvent.getRequestModeration());
        }

        if (updateEvent.getTitle() != null) {
            event.setTitle(updateEvent.getTitle());
        }

        if (updateEvent.getStateAction() != null) {
            switch (updateEvent.getStateAction()) {
                case PUBLISH_EVENT:
                    if (event.getState() != State.PENDING) {
                        throw new ConflictException("Событие можно опубликовать только из состояния ожидания публикации");
                    }
                    event.setState(State.PUBLISHED);
                    event.setPublishedOn(LocalDateTime.now());
                    break;
                case REJECT_EVENT:
                    if (event.getState() == State.PUBLISHED) {
                        throw new ConflictException("Опубликованное событие нельзя отклонить");
                    }
                    event.setState(State.CANCELED);
                    break;
                default:
                    throw new ConflictException("Иное действие, недоступное администратору " + updateEvent.getStateAction());
            }
        }
        return event;
    }
}
