package ru.practicum.ewmservice.compilation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewmservice.compilation.dto.CompilationDto;
import ru.practicum.ewmservice.compilation.dto.NewCompilationDto;
import ru.practicum.ewmservice.compilation.dto.UpdateCompilationRequest;
import ru.practicum.ewmservice.compilation.service.AdminCompilationsService;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/compilations")
public class AdminCompilationsController {

    private final AdminCompilationsService eventsService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto saveCompilations(@Valid @RequestBody NewCompilationDto newCompilationDto) {
        return eventsService.saveCompilations(newCompilationDto);
    }

    @PatchMapping("/{compId}")
    @ResponseStatus(HttpStatus.OK)
    public CompilationDto updateCompilationsById(@Valid @RequestBody UpdateCompilationRequest updateCompilationRequest,
                                                 @PathVariable("compId") Long compId) {
        return eventsService.updateCompilationById(updateCompilationRequest, compId);
    }

    @DeleteMapping("/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long compId) {
        eventsService.deleteById(compId);
    }
}