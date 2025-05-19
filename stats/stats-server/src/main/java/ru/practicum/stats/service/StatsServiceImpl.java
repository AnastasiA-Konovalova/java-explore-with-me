package ru.practicum.stats.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.StatsRequestDto;
import ru.practicum.stats.mapper.EndpointHitMapper;
import ru.practicum.stats.model.App;
import ru.practicum.stats.model.EndpointHit;
import ru.practicum.stats.projection.ViewStatsProjection;
import ru.practicum.stats.repository.AppRepository;
import ru.practicum.stats.repository.StatsRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {

    private final StatsRepository statsRepository;
    private final AppRepository appRepository;

    @Override
    @Transactional
    public void save(EndpointHitDto endpointHitDto) {
        String appName = endpointHitDto.getApp();
        App app = appRepository.findByName(appName)
                .orElseGet(() -> appRepository.save(new App(appName)));
        EndpointHit endpointHit = EndpointHitMapper.toEntity(endpointHitDto, app);
        endpointHit.setApp(app);

        statsRepository.save(endpointHit);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ViewStatsProjection> get(StatsRequestDto statsRequestDto) {
        if (statsRequestDto.getUris() == null || statsRequestDto.getUris().isEmpty()) {
            if (statsRequestDto.getUnique()) {
                return statsRepository.findAllNotUrisStatsUnique(statsRequestDto.getStart(), statsRequestDto.getEnd());
            } else {
                return statsRepository.findAllNotUrisStats(statsRequestDto.getStart(), statsRequestDto.getEnd());
            }
        } else {
            if (statsRequestDto.getUnique()) {
                return statsRepository.findAllStatsUnique(statsRequestDto.getStart(), statsRequestDto.getEnd(), statsRequestDto.getUris());
            } else {
                return statsRepository.findAllStats(statsRequestDto.getStart(), statsRequestDto.getEnd(), statsRequestDto.getUris());
            }
        }
    }
}