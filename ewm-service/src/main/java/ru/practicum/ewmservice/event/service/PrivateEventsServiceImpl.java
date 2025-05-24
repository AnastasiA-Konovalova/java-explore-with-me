package ru.practicum.ewmservice.event.service;

import exeptions.InternalServerException;
import exeptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewmservice.category.mapper.CategoryMapper;
import ru.practicum.ewmservice.category.model.Category;
import ru.practicum.ewmservice.category.service.PublicCategoriesService;
import ru.practicum.ewmservice.event.dto.EventFullDto;
import ru.practicum.ewmservice.event.dto.EventRequestStatusUpdateRequest;
import ru.practicum.ewmservice.event.dto.EventShortDto;
import ru.practicum.ewmservice.event.dto.NewEventDtoRequest;
import ru.practicum.ewmservice.event.dto.ParticipationRequestDto;
import ru.practicum.ewmservice.event.dto.UpdateEventUserRequest;
import ru.practicum.ewmservice.event.mapper.EventMapper;
import ru.practicum.ewmservice.event.mapper.UpdateEventMapper;
import ru.practicum.ewmservice.event.model.Event;
import ru.practicum.ewmservice.event.model.Location;
import ru.practicum.ewmservice.event.model.State;
import ru.practicum.ewmservice.event.repository.LocationRepository;
import ru.practicum.ewmservice.event.repository.PrivateEventsRepository;
import ru.practicum.ewmservice.exception.ConflictException;
import ru.practicum.ewmservice.exception.ValidationException;
import ru.practicum.ewmservice.user.model.User;
import ru.practicum.ewmservice.user.service.AdminUsersService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PrivateEventsServiceImpl implements PrivateEventsService {

    private final PrivateEventsRepository eventsRepository;
    private final PublicCategoriesService categoriesService;
    private final AdminUsersService usersService;
    private final LocationRepository locationRepository;

    public EventFullDto saveEvent(NewEventDtoRequest eventDtoRequest, Integer userId) {
        if (LocalDateTime.parse(eventDtoRequest.getEventDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                .isBefore(LocalDateTime.now())
                && LocalDateTime.parse(eventDtoRequest.getEventDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                .isBefore(LocalDateTime.now().plusHours(2))) {
            throw new InternalServerException("Дата события " + eventDtoRequest.getEventDate() + " указана неверно. " +
                    "Событие не может начаться раньше чем через два часа от публикации и раньше чем текущее время.");
        }
        Category category = CategoryMapper.toEntityCategoryUpdate(categoriesService.getById(eventDtoRequest.getCategory()));

        User initiator = usersService.getUserById(userId);
        Location location = locationRepository.save(eventDtoRequest.getLocation());

        System.out.println(eventDtoRequest.getEventDate() + " Service");

        Event event = EventMapper.toEntityForNewEvent(eventDtoRequest, category, userId, location);
        System.out.println(event.getEventDate() + "SERVICE AFTER");
        event.setCreatedOn(LocalDateTime.now());
        event.setConfirmedRequests(0);
        event.setLocation(location);
        event.setState(State.PENDING);
        event.setInitiator(initiator);
        System.out.println(event);
        //todo добавить репозиторий локации
        // мы должны вернуть полное дто, делать сетинг недостающих полей

        return EventMapper.toFullDto(eventsRepository.save(event));
    }

    @Override
    public EventFullDto updateEvent(UpdateEventUserRequest updateEvent, Integer userId, Integer eventId) {
        usersService.getUserById(userId);
        Event event = getById(eventId);
        checkConditions(event, userId, updateEvent);
        Category category = CategoryMapper.toEntityCategoryUpdate(categoriesService.getById(updateEvent.getCategory().getId()));
        Location location = locationRepository.save(updateEvent.getLocation());

        event = UpdateEventMapper.toEvent(updateEvent, event, category, location);

        Event updatedEvent = eventsRepository.save(event);
        return EventMapper.toFullDto(updatedEvent);
    }

    @Override
    public EventFullDto updateEventRequests(EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest, Integer userId, Integer eventId) {
        return null;
    }

    @Override
    public List<EventShortDto> getEventByUserId(Integer userId, Integer from, Integer size) {
        return null;
    }

    @Override
    public EventFullDto getFullEventInfoByUserId(Integer userId, Integer eventId) {
        return null;
    }

    @Override
    public ParticipationRequestDto getRequestForEventById(Integer userId, Integer eventId) {
        return null;
    }

    private Event getById(Integer eventId) {
        return eventsRepository.findById(eventId).orElseThrow(() -> new NotFoundException("Событие с таким id " + eventId + " не найдено"));
    }

    private void checkConditions(Event event, Integer userId, UpdateEventUserRequest updateEvent) {
        if (!event.getInitiator().getId().equals(userId)) {
            throw new ValidationException("Событие может изменять только пользователь, его создавший с id " + userId + ".");
        }

        if (event.getState().equals(State.PUBLISHED)) {
            throw new ConflictException("Событие должно быть либо отменено, либо находится в состоянии ожидания.");
        }

        if (LocalDateTime.parse(updateEvent.getEventDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                .isBefore(LocalDateTime.now())
                && LocalDateTime.parse(updateEvent.getEventDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                .isBefore(LocalDateTime.now().plusHours(2))) {
            throw new InternalServerException("Дата события " + updateEvent.getEventDate() + " указана неверно. " +
                    "Событие не может начаться раньше чем через два часа от публикации и раньше чем текущее время.");
        }
    }

}
