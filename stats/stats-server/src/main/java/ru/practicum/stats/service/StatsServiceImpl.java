package ru.practicum.stats.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.practicum.dto.ViewStatsDto;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.stats.mapper.EndpointHitMapper;
import ru.practicum.stats.model.EndpointHit;
import ru.practicum.stats.repository.StatsRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

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
        System.out.println(statsRepository.save(endpointHit));

//        {
//            "app": "ewm-main-service",
//                "uri": "/events/1",
//                "ip": "192.163.0.1",
//                "timestamp": "2022-09-06 11:00:23"
//        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ViewStatsDto> get(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        if (start == null || end == null || start.isAfter(end)) {
            throw new IllegalArgumentException("Начало " + start + " и конец временного промежутка " + end + " должны " +
                    "быть указаны и быть в верной последовательности");
        }

        //List<ViewStatsDto> hits;
        //List<EndpointHitDto> hits;

        if (unique) {
            System.out.println(statsRepository.findAllStatsUnique(start, end, uris));
            return statsRepository.findAllStatsUnique(start, end, uris);
        } else {
            System.out.println(statsRepository.findAllStats(start, end, uris));
            return statsRepository.findAllStats(start, end, uris);
        }

//        if (hits == null) {
//            throw new NoSuchElementException("Получение статистики по посещениям невозможно в виду ее отсутствия " +
//                    "для запрошенных событий");
//        }
        //System.out.println(hits);
        //return EndpointHitMapper.toViewDto(hits);
    }
}
