package ru.practicum.ewmservice.event.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.ewmservice.event.dto.EventFullDto;
import ru.practicum.ewmservice.event.dto.EventShortDto;
import ru.practicum.ewmservice.event.dto.ShortEventDtoRequest;
import ru.practicum.ewmservice.event.enums.EventSortOption;
import ru.practicum.ewmservice.event.enums.State;
import ru.practicum.ewmservice.event.mapper.EventMapper;
import ru.practicum.ewmservice.event.model.Event;
import ru.practicum.ewmservice.event.repository.PublicEventsRepository;
import ru.practicum.ewmservice.event.service.specifications.EventSpecifications;
import ru.practicum.ewmservice.exception.NotFoundException;
import ru.practicum.ewmservice.exception.ValidationException;
import ru.practicum.stats.StatsClient;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PublicEventsServiceImpl implements PublicEventsService {

    private final PublicEventsRepository eventsRepository;
    private final StatsClient statsClient;

    @Override
    public List<EventShortDto> getEvents(ShortEventDtoRequest eventDtoRequest,
                                         Long from, Long size, HttpServletRequest request) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        if (eventDtoRequest.getRangeStart() != null && eventDtoRequest.getRangeEnd()!= null) {
            LocalDateTime start = LocalDateTime.parse(eventDtoRequest.getRangeStart(), formatter);
            LocalDateTime end = LocalDateTime.parse(eventDtoRequest.getRangeEnd(), formatter);
            if (start.isAfter(end)) {
                throw new ValidationException("rangeStart не может быть позже rangeEnd");
            }
        }
        Specification<Event> specification = EventSpecifications.filterEventConditionals(eventDtoRequest.getText(),
                eventDtoRequest.getCategories(), eventDtoRequest.getPaid(), eventDtoRequest.getRangeStart(),
                eventDtoRequest.getRangeEnd(), eventDtoRequest.getOnlyAvailable());
        Sort sortBy = Sort.unsorted();
        if (eventDtoRequest.getSort() != null) {
            if (eventDtoRequest.getSort().equals(EventSortOption.EVENT_DATE)) {
                sortBy = Sort.by(Sort.Direction.ASC, "eventDate");
            } else if (eventDtoRequest.getSort().equals(EventSortOption.VIEWS)) {
                sortBy = Sort.by(Sort.Direction.DESC, "views");
            }
        }
        saveStats(request);

        PageRequest pageRequest = PageRequest.of((int) (from / size), size.intValue(), sortBy);
        Page<Event> eventPage = eventsRepository.findAll(specification, pageRequest);

        return eventPage.getContent().stream()
                .map(EventMapper::toShortDto)
                .collect(Collectors.toList());
    }

    public EventFullDto getById(Long id, HttpServletRequest request) {
        Event event = eventsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Событие с id " + id + " не найдено."));

        if (!event.getState().equals(State.PUBLISHED)) {
            throw new NotFoundException("Событие с id " + id + " не опубликовано");
        }
        saveStats(request);
        if (event.getViews() == null) {
            event.setViews(1L);
        } else {
            event.setViews(event.getViews() + 1);
        }
        return EventMapper.toFullDto(event);
    }

    private void saveStats(HttpServletRequest request) {
        EndpointHitDto endpointHitDto = new EndpointHitDto(
                "ewm-service",
                request.getRequestURI(),
                request.getRemoteAddr(),
                LocalDateTime.now()
        );
        statsClient.save(endpointHitDto);
    }
}