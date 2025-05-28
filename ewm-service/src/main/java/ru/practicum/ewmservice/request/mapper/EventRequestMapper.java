package ru.practicum.ewmservice.request.mapper;

import ru.practicum.ewmservice.request.dto.ParticipationRequestDto;
import ru.practicum.ewmservice.request.model.Request;

public class EventRequestMapper {

    public static ParticipationRequestDto toDto(Request saveRequest) {
        ParticipationRequestDto requestDto = new ParticipationRequestDto();
        requestDto.setId(saveRequest.getId());
        requestDto.setEvent(saveRequest.getEvent().getId());
        requestDto.setRequester(saveRequest.getRequester().getId());
        requestDto.setCreated(saveRequest.getCreated());
        requestDto.setStatus(saveRequest.getStatus());

        return requestDto;
    }
}