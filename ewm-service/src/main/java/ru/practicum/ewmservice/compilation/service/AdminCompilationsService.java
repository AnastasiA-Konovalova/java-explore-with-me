package ru.practicum.ewmservice.compilation.service;

import ru.practicum.ewmservice.compilation.dto.CompilationDto;
import ru.practicum.ewmservice.compilation.dto.NewCompilationDto;
import ru.practicum.ewmservice.compilation.dto.UpdateCompilationRequest;

public interface AdminCompilationsService {

    CompilationDto saveCompilations(NewCompilationDto newCompilationDto);

    CompilationDto updateCompilationById(UpdateCompilationRequest updateCompilationRequest, Long compId);

    void deleteById(Long compId);
}