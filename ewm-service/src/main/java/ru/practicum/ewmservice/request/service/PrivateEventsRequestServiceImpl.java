package ru.practicum.ewmservice.request.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewmservice.event.enums.State;
import ru.practicum.ewmservice.event.enums.Status;
import ru.practicum.ewmservice.event.model.Event;
import ru.practicum.ewmservice.event.repository.PrivateEventsRepository;
import ru.practicum.ewmservice.event.service.PrivateEventsService;
import ru.practicum.ewmservice.exception.ConflictException;
import ru.practicum.ewmservice.exception.NotFoundException;
import ru.practicum.ewmservice.exception.ValidationException;
import ru.practicum.ewmservice.request.dto.ParticipationRequestDto;
import ru.practicum.ewmservice.request.mapper.EventRequestMapper;
import ru.practicum.ewmservice.request.model.Request;
import ru.practicum.ewmservice.request.repository.PrivateEventsRequestRepository;
import ru.practicum.ewmservice.user.model.User;
import ru.practicum.ewmservice.user.service.AdminUsersService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PrivateEventsRequestServiceImpl implements PrivateEventsRequestService {

    private final PrivateEventsRepository eventsRepository;
    private final PrivateEventsRequestRepository eventsRequestRepository;
    private final AdminUsersService usersService;
    private final PrivateEventsService eventsService;
    private final PrivateEventsRepository privateEventsRepository;

    @Override
    public List<ParticipationRequestDto> getRequestForStrangerEvent(Long userId) {
        usersService.getUserById(userId);
        List<Request> requests = eventsRequestRepository.getByUserId(userId);

        if (requests.isEmpty()) {
            return List.of();
        }
        return requests.stream()
                .map(EventRequestMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ParticipationRequestDto saveEventRequest(Long userId, Long eventId) {
        User user = usersService.getUserById(userId);
        Event event = eventsService.getById(eventId);

        Request request = new Request();
        if (event.getInitiator().getId().equals(userId)) {
            throw new ConflictException("Инициатор события не может добавить запрос на участие в своём событии.");
        }
        if (!event.getState().equals(State.PUBLISHED)) {
            throw new ConflictException("Нельзя участвовать в неопубликованном событии.");
        }
        if (event.getParticipantLimit() > 0 && event.getParticipantLimit() <= event.getConfirmedRequests()) {
            throw new ConflictException("Лимит записи на участие в событии достигнут.");
        }

        boolean alreadyExists = eventsRequestRepository.existsByRequesterIdAndEventId(userId, eventId);
        if (alreadyExists) {
            throw new ConflictException("Повторный запрос невозможен.");
        }

        request.setEvent(event);
        request.setCreated(LocalDateTime.now());
        request.setRequester(user);

        if (!event.getRequestModeration() || event.getParticipantLimit() == 0) {
            request.setStatus(Status.CONFIRMED);
            event.setConfirmedRequests(event.getConfirmedRequests() + 1);
            privateEventsRepository.save(event);
        } else {
            request.setStatus(Status.PENDING);
        }

//        if (request.getStatus().equals(Status.CONFIRMED)) {
//            event.setConfirmedRequests(event.getConfirmedRequests() + 1);
//            privateEventsRepository.save(event);
//        }


        Request saveRequest = eventsRequestRepository.save(request);

        //нельзя добавить повторный запрос (Ожидается код ошибки 409)
        // указала ограничение в таблице
        return EventRequestMapper.toDto(saveRequest);
    }

    @Override
    public ParticipationRequestDto updateCancelRequest(Long userId, Long requestId) {
        User user = usersService.getUserById(userId);
        Request request = requestById(requestId);
        if (!request.getRequester().equals(user)) {
            throw new ValidationException("Пользователь " + userId + " не может отменить запрос на участие в событии," +
                    " в связи с отсутствием в списке участвующих");
        }
        request.setStatus(Status.CANCELED);
        Request update = eventsRequestRepository.save(request);
        //eventsRequestRepository.deleteById(requestId);

        return EventRequestMapper.toDto(update);
    }

    private Request requestById(Long requestId) {
        return eventsRequestRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException("Запрос по id " + " не найден"));
    }
}