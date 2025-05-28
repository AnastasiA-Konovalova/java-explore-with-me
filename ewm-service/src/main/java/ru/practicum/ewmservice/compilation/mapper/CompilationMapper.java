package ru.practicum.ewmservice.compilation.mapper;

import ru.practicum.ewmservice.compilation.dto.CompilationDto;
import ru.practicum.ewmservice.compilation.dto.NewCompilationDto;
import ru.practicum.ewmservice.compilation.dto.UpdateCompilationRequest;
import ru.practicum.ewmservice.compilation.model.Compilation;
import ru.practicum.ewmservice.event.mapper.EventMapper;
import ru.practicum.ewmservice.event.model.Event;

import java.util.List;
import java.util.stream.Collectors;

public class CompilationMapper {

    public static CompilationDto toDto(Compilation compilation) {
        CompilationDto compilationDto = new CompilationDto();
        compilationDto.setId(compilation.getId());
        compilationDto.setTitle(compilation.getTitle());
        compilationDto.setPinned(compilation.getPinned());
        compilationDto.setEvents(
                compilation.getEvents().stream()
                        .map(EventMapper::toShortDto)
                        .collect(Collectors.toList())
        );
        return compilationDto;
    }

    public static Compilation toEntity(NewCompilationDto newCompilationDto, List<Event> event) {
        Compilation compilation = new Compilation();
        compilation.setEvents(event);
        compilation.setPinned(newCompilationDto.getPinned() != null ? newCompilationDto.getPinned() : false);
        compilation.setTitle(newCompilationDto.getTitle());
        return compilation;
    }

    public static Compilation toEntityUpdate(UpdateCompilationRequest compilationRequest,
                                             List<Event> events, Compilation compilation) {
        if (compilationRequest.getEvents() != null && !events.isEmpty()) {
            compilation.setEvents(events);
        }
        if (compilationRequest.getPinned() != null) {
            compilation.setPinned(compilationRequest.getPinned());
        }
        if (compilationRequest.getTitle() != null) {
            compilation.setTitle(compilationRequest.getTitle());
        }
        return compilation;
    }
}