package ru.practicum.stats.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.EndpointHitProjection;
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

        List<EndpointHitProjection> result;

        if (uris.isEmpty()) {
            if (unique) {
                result = statsRepository.findAllNotUrisUnique(start, end);
            } else {
                result = statsRepository.findAllNotUris(start, end);
            }

        } else {
            if (unique) {
                result = statsRepository.findAllWithUrisUnique(start, end, uris);
            } else {
                result = statsRepository.findAllWithUris(start, end, uris);
            }

        }

        return result.stream()
                .map(hitProjection -> ViewStatsDto.builder()
                        .app(hitProjection.getApp())
                        .uri(hitProjection.getUri())
                        .hits(hitProjection.getHits())
                        .build())
                .toList();
    }
}