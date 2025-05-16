package ru.practicum.stats.mapper;

import ru.practicum.dto.EndpointHitDto;
import ru.practicum.stats.model.EndpointHit;

public class EndpointHitMapper {

    public static EndpointHitDto toDto(EndpointHit endpointHit) {
        EndpointHitDto endpointHitDto = new EndpointHitDto();
        endpointHitDto.setIp(endpointHit.getIp());
        endpointHitDto.setApp(endpointHit.getApp());
        endpointHitDto.setUri(endpointHit.getUri());
        endpointHitDto.setTimestamp(endpointHit.getTimestamp());

        return endpointHitDto;
    }

    public static EndpointHit toEntity(EndpointHitDto endpointHitDto) {
        EndpointHit endpointHit = new EndpointHit();
        endpointHit.setIp(endpointHitDto.getIp());
        endpointHit.setApp(endpointHitDto.getApp());
        endpointHit.setUri(endpointHitDto.getUri());
        endpointHit.setTimestamp(endpointHitDto.getTimestamp());

        return endpointHit;
    }
}