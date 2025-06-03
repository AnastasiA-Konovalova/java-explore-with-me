package ru.practicum.ewmservice.event.service;

import exeptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.ewmservice.category.mapper.CategoryMapper;
import ru.practicum.ewmservice.category.model.Category;
import ru.practicum.ewmservice.category.service.PublicCategoriesService;
import ru.practicum.ewmservice.event.dto.EventFullDto;
import ru.practicum.ewmservice.event.dto.EventShortDto;
import ru.practicum.ewmservice.event.dto.NewEventDtoRequest;
import ru.practicum.ewmservice.event.enums.State;
import ru.practicum.ewmservice.event.enums.Status;
import ru.practicum.ewmservice.event.mapper.EventMapper;
import ru.practicum.ewmservice.event.mapper.UpdateEventMapper;
import ru.practicum.ewmservice.event.model.Event;
import ru.practicum.ewmservice.event.model.EventRequestStatusUpdateRequest;
import ru.practicum.ewmservice.event.model.EventRequestStatusUpdateResult;
import ru.practicum.ewmservice.event.model.Location;
import ru.practicum.ewmservice.event.model.UpdateEventUserRequest;
import ru.practicum.ewmservice.event.repository.LocationRepository;
import ru.practicum.ewmservice.event.repository.PrivateEventsRepository;
import ru.practicum.ewmservice.exception.ConflictException;
import ru.practicum.ewmservice.exception.ValidationException;
import ru.practicum.ewmservice.request.dto.ParticipationRequestDto;
import ru.practicum.ewmservice.request.mapper.EventRequestMapper;
import ru.practicum.ewmservice.request.model.Request;
import ru.practicum.ewmservice.request.repository.EventRequestRepository;
import ru.practicum.ewmservice.user.model.User;
import ru.practicum.ewmservice.user.service.AdminUsersService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PrivateEventsServiceImpl implements PrivateEventsService {

    private final PrivateEventsRepository eventsRepository;
    private final PublicCategoriesService categoriesService;
    private final AdminUsersService usersService;
    private final LocationRepository locationRepository;
    private final EventRequestRepository eventsRequestRepository;

    public EventFullDto saveEvent(NewEventDtoRequest eventDtoRequest, Long userId) {
        if (LocalDateTime.parse(eventDtoRequest.getEventDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                .isBefore(LocalDateTime.now())
                && LocalDateTime.parse(eventDtoRequest.getEventDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                .isBefore(LocalDateTime.now().plusHours(2))) {
            throw new ValidationException("Дата события " + eventDtoRequest.getEventDate() + " указана неверно. " +
                    "Событие не может начаться раньше чем через два часа от публикации и раньше чем текущее время.");
        }
        Category category = CategoryMapper.toEntityCategory(categoriesService.getById(eventDtoRequest.getCategory()));

        User initiator = usersService.getUserById(userId);
        Location location = locationRepository.save(eventDtoRequest.getLocation());

        Event event = EventMapper.toEntityForNewEvent(eventDtoRequest, category, userId, location);
        event.setCreatedOn(LocalDateTime.now());
        event.setConfirmedRequests(0L);
        event.setLocation(location);
        event.setState(State.PENDING);
        event.setInitiator(initiator);

        return EventMapper.toFullDto(eventsRepository.save(event));
    }

    @Override
    public EventFullDto updateEvent(UpdateEventUserRequest updateEvent, Long userId, Long eventId) {
        usersService.getUserById(userId);
        Event event = getById(eventId);
        checkConditionsForUpdate(event, userId, updateEvent.getEventDate());
        Category category = null;
        Location location = null;
        if (updateEvent.getCategory() != null) {
            category = CategoryMapper.toEntityCategory(categoriesService.getById(updateEvent.getCategory().getId()));
        }
        if (updateEvent.getLocation() != null) {
            location = locationRepository.save(updateEvent.getLocation());
        }
        event = UpdateEventMapper.toEvent(updateEvent, event, category, location);
        Event updatedEvent = eventsRepository.save(event);

        return EventMapper.toFullDto(updatedEvent);
    }

    @Override
    public EventRequestStatusUpdateResult updateEventRequests(EventRequestStatusUpdateRequest eventRequest, Long userId, Long eventId) {
        usersService.getUserById(userId);
        Event event = getById(eventId);

        if (!event.getInitiator().getId().equals(userId)) {
            throw new ConflictException("Только инициатор может изменять статус заявок.");
        }
        if (event.getParticipantLimit() == 0 || !event.getRequestModeration()) {
            return new EventRequestStatusUpdateResult(List.of(), List.of());
        }

        List<Request> allByEventId = eventsRequestRepository.findAllByEventId(eventId);
        List<Request> list = allByEventId.stream()
                .filter(request -> request.getStatus().equals(Status.CONFIRMED))
                .toList();
        if (list.size() == event.getParticipantLimit()) {
            throw new ConflictException("Нельзя подтвердить заявку, если уже достигнут лимит по заявкам на данное событие.");
        }

        List<Request> requests = eventsRequestRepository.findAllById(eventRequest.getRequestIds());

        List<ParticipationRequestDto> confirmed = new ArrayList<>();
        List<ParticipationRequestDto> rejected = new ArrayList<>();

        for (Request request : requests) {
            if (!request.getStatus().equals(Status.PENDING)) {
                throw new ConflictException("Можно изменить только заявки в статусе PENDING.");
            }

            if (eventRequest.getStatus().equals(Status.CONFIRMED)) {
                if (event.getConfirmedRequests() >= event.getParticipantLimit()) {
                    request.setStatus(Status.REJECTED);
                    rejected.add(EventRequestMapper.toDto(request));
                } else {
                    request.setStatus(Status.CONFIRMED);
                    event.setConfirmedRequests(event.getConfirmedRequests() + 1);
                    confirmed.add(EventRequestMapper.toDto(request));
                }
            } else if (eventRequest.getStatus().equals(Status.REJECTED)) {
                request.setStatus(Status.REJECTED);
                rejected.add(EventRequestMapper.toDto(request));
            } else {
                throw new ConflictException("Недопустимый статус: " + eventRequest.getStatus());
            }
        }
        eventsRepository.save(event);
        eventsRequestRepository.saveAll(requests);

        return new EventRequestStatusUpdateResult(confirmed, rejected);
    }

    @Override
    public List<EventShortDto> getEventByUserId(Long userId, Long from, Long size) {
        usersService.getUserById(userId);

        PageRequest pageRequest = PageRequest.of((int) (from / size), size.intValue());

        List<Event> events = eventsRepository.findAllByInitiatorId(userId, pageRequest).getContent();
        if (events.isEmpty()) {
            return List.of();
        }
        return events.stream()
                .map(EventMapper::toShortDto)
                .toList();
    }

    @Override
    public EventFullDto getFullEventInfoByUserId(Long userId, Long eventId) {
        return EventMapper.toFullDto(checkMainConditions(userId, eventId));
    }

    @Override
    public List<ParticipationRequestDto> getRequestForEventById(Long userId, Long eventId) {
        checkMainConditions(userId, eventId);
        List<Request> requests = eventsRequestRepository.findAllByEventId(eventId);

        return requests.stream()
                .map(EventRequestMapper::toDto)
                .toList();
    }

    public Event getById(Long eventId) {
        return eventsRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Событие с id " + eventId + " не найдено"));
    }

    private Event checkMainConditions(Long userId, Long eventId) {
        usersService.getUserById(userId);
        Event event = getById(eventId);
        if (!event.getInitiator().getId().equals(userId)) {
            throw new ValidationException("Событие может изменять только пользователь, его создавший с id " + userId + ".");
        }
        return event;
    }

    private void checkConditionsForUpdate(Event event, Long userId, String eventDate) {
        if (!event.getInitiator().getId().equals(userId)) {
            throw new ValidationException("Событие может изменять только пользователь, его создавший с id " + userId + ".");
        }

        if (event.getState().equals(State.PUBLISHED)) {
            throw new ConflictException("Событие должно быть либо отменено, либо находится в состоянии ожидания.");
        }

        if (eventDate != null && LocalDateTime.parse(eventDate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                .isBefore(LocalDateTime.now())
                && LocalDateTime.parse(eventDate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                .isBefore(LocalDateTime.now().plusHours(2))) {
            throw new ValidationException("Дата события " + eventDate + " указана неверно. " +
                    "Событие не может начаться раньше чем через два часа от публикации и раньше чем текущее время.");
        }
    }
}