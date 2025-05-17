package ru.practicum.stats.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.StatsRequestDto;
import ru.practicum.stats.mapper.EndpointHitMapper;
import ru.practicum.stats.model.EndpointHit;
import ru.practicum.stats.projection.ViewStatsProjection;
import ru.practicum.stats.repository.StatsRepository;

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
    public List<ViewStatsProjection> get(StatsRequestDto statsRequestDto) {
//        if (start == null || end == null || start.isAfter(end)) {
//            throw new IllegalArgumentException("Начало " + start + " и конец временного промежутка " + end + " должны " +
//                    "быть указаны и быть в верной последовательности");
//        }

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