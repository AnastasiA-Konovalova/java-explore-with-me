package ru.practicum.ewmservice.event.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class FullEventDtoRequest {

    private List<Long> users;

    private List<String> states;

    private List<Long> categories;

    private String rangeStart;

    private String rangeEnd;
//    @JsonProperty(defaultValue = "0")
//    private Long from;
//
//    @JsonProperty(defaultValue = "10")
//    private Long size;
}
