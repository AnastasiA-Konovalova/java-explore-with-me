package ru.practicum.ewmservice.compilation.mapper;

import ru.practicum.ewmservice.compilation.dto.CompilationDto;
import ru.practicum.ewmservice.compilation.dto.NewCompilationDto;
import ru.practicum.ewmservice.compilation.dto.UpdateCompilationRequest;
import ru.practicum.ewmservice.compilation.model.Compilation;
import ru.practicum.ewmservice.event.mapper.EventMapper;

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

    public static Compilation toEntity(NewCompilationDto newCompilationDto) {
        Compilation compilation = new Compilation();
        compilation.setPinned(newCompilationDto.getPinned() != null ? newCompilationDto.getPinned() : false);
        compilation.setTitle(newCompilationDto.getTitle());
        return compilation;
    }

    public static Compilation toEntityUpdate(UpdateCompilationRequest compilationRequest, Compilation compilation) {
        if (compilationRequest.getPinned() != null) {
            compilation.setPinned(compilationRequest.getPinned());
        }
        if (compilationRequest.getTitle() != null) {
            compilation.setTitle(compilationRequest.getTitle());
        }

        return compilation;
    }
}