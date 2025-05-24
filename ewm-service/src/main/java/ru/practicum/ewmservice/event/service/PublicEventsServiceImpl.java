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
import ru.practicum.ewmservice.event.mapper.EventMapper;
import ru.practicum.ewmservice.event.model.Event;
import ru.practicum.ewmservice.event.model.State;
import ru.practicum.ewmservice.event.repository.PublicEventsRepository;
import ru.practicum.ewmservice.event.service.specifications.EventSpecifications;
import ru.practicum.ewmservice.exception.NotFoundException;
import ru.practicum.stats.StatsClient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PublicEventsServiceImpl implements PublicEventsService {

    private final PublicEventsRepository eventsRepository;
    private final StatsClient statsClient;

    @Override
    public List<EventShortDto> getEvents(String text, List<Integer> categories, Boolean paid, String rangeStart,
                                         String rangeEnd, Boolean onlyAvailable, String sort,
                                         Integer from, Integer size, HttpServletRequest request) {
        Specification<Event> specification = EventSpecifications.filterEventConditionals(text, categories, paid, rangeStart, rangeEnd, onlyAvailable);
        Sort sortBy = Sort.unsorted();
        if ("event_date".equalsIgnoreCase(sort)) {
            sortBy = Sort.by(Sort.Direction.ASC, "eventDate");
        } else if ("views".equalsIgnoreCase(sort)) {
            sortBy = Sort.by(Sort.Direction.DESC, "views");
        }

        saveStats(request);

        PageRequest pageRequest = PageRequest.of(from / size, size, sortBy);
        Page<Event> eventPage = eventsRepository.findAll(specification, pageRequest);

//        List<String> uris = eventPage.getContent().stream()
//                .map(event -> "/events" + event.getId())
//                .toList();
//
//        List<ViewStatsDto> viewStatsDto = statsClient.get(statsR)

        return eventPage.getContent().stream()
                .map(EventMapper::toShortDto)
                .collect(Collectors.toList());
    }

    public EventFullDto getById(Integer id, HttpServletRequest request) {
        Event event = eventsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Событие с id " + id + " не найдено."));

        if (!event.getState().equals(State.PUBLISHED)) {
            throw new NotFoundException("Событие с id " + id + " не опубликовано");
        }
        saveStats(request);

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
