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

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class AdminCompilationsServiceImpl implements AdminCompilationsService {

    private final CompilationsRepository compilationsRepository;
    private final AdminEventsRepository eventsRepository;

    @Override
    public CompilationDto saveCompilations(NewCompilationDto newCompilationDto) {
        if (newCompilationDto.getEvents() == null) {
            newCompilationDto.setEvents(new ArrayList<>());
        }
        List<Event> events = eventsRepository.findAllById(newCompilationDto.getEvents());

        Compilation compilation = CompilationMapper.toEntity(newCompilationDto, events);
        return CompilationMapper.toDto(compilationsRepository.save(compilation));
    }

    @Override
    public CompilationDto updateCompilationById(UpdateCompilationRequest updateCompilationRequest, Long compId) {
        Compilation compilation = getById(compId);
        List<Event> events = new ArrayList<>();
        if (updateCompilationRequest.getEvents() != null && !updateCompilationRequest.getEvents().isEmpty()) {
            events = eventsRepository.findAllById(updateCompilationRequest.getEvents());
        }

        Compilation update = CompilationMapper.toEntityUpdate(updateCompilationRequest, events, compilation);
        Compilation save = compilationsRepository.save(update);

        return CompilationMapper.toDto(save);
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