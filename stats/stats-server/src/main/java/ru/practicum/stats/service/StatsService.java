package ru.practicum.stats.service;

import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.StatsRequestDto;
import ru.practicum.stats.projection.ViewStatsProjection;

import java.util.List;

public interface StatsService {

    void save(EndpointHitDto endpointHitDto);

    List<ViewStatsProjection> get(StatsRequestDto statsRequestDto);
}