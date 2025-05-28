package ru.practicum.ewmservice.compilation.service;

import ru.practicum.ewmservice.compilation.dto.CompilationDto;

import java.util.List;

public interface PublicCompilationsService {

    List<CompilationDto> getWithConditions(Boolean pinned, Long from, Long size);

    CompilationDto getById(Long compId);
}