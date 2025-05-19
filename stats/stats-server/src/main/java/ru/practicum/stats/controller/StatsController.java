package ru.practicum.stats.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.StatsRequestDto;
import ru.practicum.stats.projection.ViewStatsProjection;
import ru.practicum.stats.service.StatsService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StatsController {

    private final StatsService statsService;

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@Valid @RequestBody EndpointHitDto endpointHitDto) {
        statsService.save(endpointHitDto);
    }

    @GetMapping("/stats")
    public List<ViewStatsProjection> getStats(@Valid @ModelAttribute StatsRequestDto statsRequestDto) {
        if (statsRequestDto.getStart().isAfter(statsRequestDto.getEnd())) {
            throw new IllegalArgumentException("Начало временного промежутка должно быть раньше его окончания");
        }
        List<ViewStatsProjection> stats = statsService.get(statsRequestDto);
        return ResponseEntity.ok(stats).getBody();
    }
}