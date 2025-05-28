package ru.practicum.ewmservice.event.service;

import exeptions.InternalServerException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.practicum.ewmservice.category.model.Category;
import ru.practicum.ewmservice.category.repository.CategoriesRepository;
import ru.practicum.ewmservice.event.dto.EventFullDto;
import ru.practicum.ewmservice.event.enums.State;
import ru.practicum.ewmservice.event.mapper.EventMapper;
import ru.practicum.ewmservice.event.mapper.UpdateEventMapper;
import ru.practicum.ewmservice.event.model.Event;
import ru.practicum.ewmservice.event.model.Location;
import ru.practicum.ewmservice.event.model.UpdateEventAdminRequest;
import ru.practicum.ewmservice.event.repository.AdminEventsRepository;
import ru.practicum.ewmservice.event.repository.LocationRepository;
import ru.practicum.ewmservice.event.service.specifications.EventSpecifications;
import ru.practicum.ewmservice.exception.ConflictException;
import ru.practicum.ewmservice.exception.NotFoundException;
import ru.practicum.ewmservice.exception.ValidationException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AdminEventsServiceImpl implements AdminEventsService {

    private final AdminEventsRepository eventsRepository;
    private final PrivateEventsService privateEventsService;
    private final CategoriesRepository publicCategoriesRepository;
    private final LocationRepository locationRepository;

    @Override
    public List<EventFullDto> getEventsByIds(List<Long> users, List<String> states, List<Long> categories, String rangeStart, String rangeEnd, Long from, Long size) {
        Specification<Event> specification = EventSpecifications.filterEventConditionalsForGetByIds(users, states, categories, rangeStart, rangeEnd);

        PageRequest pageRequest = PageRequest.of((int) (from / size), size.intValue());
        Page<Event> eventPage = eventsRepository.findAll(specification, pageRequest);
        System.out.println(eventPage);
        if (eventPage.isEmpty()) {
            return List.of();
        }

        return eventPage.getContent().stream()
                .map(EventMapper::toFullDto)
                .collect(Collectors.toList());
    }

    @Override
    public EventFullDto updateById(UpdateEventAdminRequest updateEvent, Long eventId) {
        Event event = checkEvent(eventId);
        if (updateEvent.getEventDate() != null) {
            LocalDateTime parsedDate = LocalDateTime.parse(updateEvent.getEventDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            if (parsedDate.isBefore(LocalDateTime.now()) || parsedDate.isBefore(LocalDateTime.now().plusHours(2))) {
                throw new ValidationException("Дата события " + updateEvent.getEventDate() + " указана неверно. " +
                        "Событие не может начаться раньше чем через два часа от публикации и раньше чем текущее время.");
            }
        }
        Category category = publicCategoriesRepository.findById(event.getCategory().getId()).orElseThrow(() -> new NotFoundException(" вакпр"));
        Location location = locationRepository.findById(event.getLocation().getId()).orElseThrow(() -> new NotFoundException(" выфчя"));
        event = UpdateEventMapper.toEventUpdateAdmin(updateEvent, event, category, location);

        return EventMapper.toFullDto(eventsRepository.save(event));
    }

    private Event checkEvent(Long eventId) {
        Event event = privateEventsService.getById(eventId);
        LocalDateTime eventDate = event.getEventDate();

        if (!event.getState().equals(State.PENDING)) {
            throw new ConflictException("Событие можно публиковать, только если оно в состоянии ожидания публикации");
        }
        if (eventDate != null
                && eventDate.isBefore(LocalDateTime.now().plusHours(1))) {
            throw new InternalServerException("Дата события " + eventDate + " указана неверно. " +
                    "Событие не может начаться раньше чем через один час от момента публикации.");
        }

        return event;
    }
}