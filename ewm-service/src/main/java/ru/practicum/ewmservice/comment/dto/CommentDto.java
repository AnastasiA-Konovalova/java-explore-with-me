package ru.practicum.ewmservice.comment.dto;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.ewmservice.comment.model.CommentStatus;
import ru.practicum.ewmservice.event.model.Event;
import ru.practicum.ewmservice.user.model.User;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentDto {

    private Long id;

    private String text;

    private Long commentator;

    private CommentStatus status;

    private Event event;

    private LocalDateTime commentTime;
}
