package ru.practicum.ewmservice.compilation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewmservice.compilation.dto.CompilationDto;
import ru.practicum.ewmservice.compilation.service.PublicCompilationsService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/compilations")
public class PublicCompilationController {

    private final PublicCompilationsService publicCompilationsService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CompilationDto> getWithConditions(@RequestParam(defaultValue = "false") Boolean pinned,
                                                  @RequestParam(defaultValue = "0") Long from,
                                                  @RequestParam(defaultValue = "10") Long size) {
        return publicCompilationsService.getWithConditions(pinned, from, size);
    }

    @GetMapping("/{compId}")
    @ResponseStatus(HttpStatus.OK)
    public CompilationDto getById(@PathVariable(name = "compId") Long compId) {
        return publicCompilationsService.getById(compId);
    }
}