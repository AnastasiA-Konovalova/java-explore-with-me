package ru.practicum.ewmservice.event.mapper;

import ru.practicum.ewmservice.event.dto.LocationDto;
import ru.practicum.ewmservice.event.model.Location;

public class LocationMapper {

    public static LocationDto toDto(Location location) {
        LocationDto locationDto = new LocationDto();
        locationDto.setLon(location.getLon());
        locationDto.setLat(location.getLat());

        return locationDto;
    }

    public static Location toEntity(LocationDto locationDto) {
        Location location = new Location();
        location.setLat(locationDto.getLat());
        location.setLon(locationDto.getLon());

        return location;
    }
}
