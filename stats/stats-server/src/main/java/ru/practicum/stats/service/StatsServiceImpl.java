package ru.practicum.stats.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStatsDto;
import ru.practicum.stats.mapper.EndpointHitMapper;
import ru.practicum.stats.model.EndpointHit;
import ru.practicum.stats.repository.StatsRepository;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {

    private final StatsRepository statsRepository;

    @Override
    @Transactional
    public void save(EndpointHitDto endpointHitDto) {
        EndpointHit endpointHit = EndpointHitMapper.toEntity(endpointHitDto);
        statsRepository.save(endpointHit);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ViewStatsDto> get(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        if (start == null || end == null) {
            throw new IllegalArgumentException("Даты не могут быть null");
        }
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Начальная дата " + start + " должна быть до конечной " + end);
        }

        boolean isUrisEmptyOrNull = uris == null || uris.isEmpty();
        if (isUrisEmptyOrNull) {
            List<ViewStatsDto> result = unique
                    ? statsRepository.findAllNotUrisStatsUnique(start, end)
                    : statsRepository.findAllNotUrisStats(start, end);
            return result;
        } else {
            List<ViewStatsDto> result = unique
                    ? statsRepository.findAllStatsUnique(start, end, uris)
                    : statsRepository.findAllStats(start, end, uris);
            return result;
        }
    }
}