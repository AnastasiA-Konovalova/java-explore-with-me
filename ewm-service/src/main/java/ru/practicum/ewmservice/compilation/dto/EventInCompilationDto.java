package ru.practicum.ewmservice.compilation.dto;

import java.time.LocalDateTime;

public interface EventInCompilationDto {
        String getAnnotation();
        String getName(); // имя категории
        Long getConfirmedRequests();
        LocalDateTime getEventDate();
        Long getInitiatorId();
        Long getUserId(); // id пользователя
        String getUserName();
        Boolean getPaid();
        String getTitle();
        Long getViews();
    }
