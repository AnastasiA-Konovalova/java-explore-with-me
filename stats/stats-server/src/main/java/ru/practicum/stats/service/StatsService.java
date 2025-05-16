package ru.practicum.stats.service;

import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStatsDto;
import ru.practicum.stats.projection.ViewStatsProjection;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsService {

    void save(EndpointHitDto endpointHitDto);

    List<ViewStatsProjection> get(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);
}