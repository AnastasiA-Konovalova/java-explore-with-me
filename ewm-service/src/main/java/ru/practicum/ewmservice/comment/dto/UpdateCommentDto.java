package ru.practicum.ewmservice.comment.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.ewmservice.comment.model.CommentStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class UpdateCommentDto {

    @Size(min = 1, max = 7000)
    private String text;

    private CommentStatus status;

    private LocalDateTime commentTimeUpdate;
}
