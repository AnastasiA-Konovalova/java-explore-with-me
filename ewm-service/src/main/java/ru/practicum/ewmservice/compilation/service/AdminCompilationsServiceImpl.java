package ru.practicum.ewmservice.compilation.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewmservice.compilation.dto.CompilationDto;
import ru.practicum.ewmservice.compilation.dto.NewCompilationDto;
import ru.practicum.ewmservice.compilation.dto.UpdateCompilationRequest;
import ru.practicum.ewmservice.compilation.mapper.CompilationMapper;
import ru.practicum.ewmservice.compilation.model.Compilation;
import ru.practicum.ewmservice.compilation.repository.CompilationsRepository;
import ru.practicum.ewmservice.event.model.Event;
import ru.practicum.ewmservice.event.repository.AdminEventsRepository;
import ru.practicum.ewmservice.exception.NotFoundException;

import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class AdminCompilationsServiceImpl implements AdminCompilationsService {

    private final CompilationsRepository compilationsRepository;
    private final AdminEventsRepository eventsRepository;

    @Override
    public CompilationDto saveCompilations(NewCompilationDto newCompilationDto) {
        Compilation compilation = CompilationMapper.toEntity(newCompilationDto);
        List<Event> events = Collections.emptyList();
        if (newCompilationDto.getEvents() != null && !newCompilationDto.getEvents().isEmpty()) {
            events = eventsRepository.findAllById(newCompilationDto.getEvents());
        }

        compilation.setEvents(events);
        return CompilationMapper.toDto(compilationsRepository.save(compilation));
    }

    @Override
    public CompilationDto updateCompilationById(UpdateCompilationRequest updateCompilationRequest, Long compId) {
        Compilation compilation = getById(compId);
        CompilationMapper.toEntityUpdate(updateCompilationRequest, compilation);

        if (updateCompilationRequest.getEvents() != null) {
            List<Event> events;
            if (updateCompilationRequest.getEvents().isEmpty()) {
                events = Collections.emptyList();
            } else {
                events = eventsRepository.findAllById(updateCompilationRequest.getEvents());
                if (events.size() != updateCompilationRequest.getEvents().size()) {
                    throw new IllegalArgumentException("Some events not found");
                }
            }
            compilation.setEvents(events);
        }

        Compilation saved = compilationsRepository.save(compilation);
        return CompilationMapper.toDto(saved);
    }

    @Override
    public void deleteById(Long compId) {
        getById(compId);
        compilationsRepository.deleteById(compId);
    }

    private Compilation getById(Long compId) {
        return compilationsRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException("Подборка с id " + compId + " не найдена."));
    }
}