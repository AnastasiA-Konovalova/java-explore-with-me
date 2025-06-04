package ru.practicum.ewmservice.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.ewmservice.comment.model.CommentStatus;

@Getter
@Setter
public class NewCommentDto {

    @NotBlank
    @Size(min = 1, max = 7000)
    private String text;

    private Long commentator;

    private CommentStatus status;

    private Long eventId;
}
