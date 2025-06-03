package ru.practicum.ewmservice.event.dto;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.ewmservice.event.enums.EventSortOption;

import java.util.List;

@Setter
@Getter
public class ShortEventDtoRequest {

    public String text;

    public List<Long> categories;

    public Boolean paid;

    public String rangeStart;

    public String rangeEnd;

    public Boolean onlyAvailable;

    public EventSortOption sort;
}
