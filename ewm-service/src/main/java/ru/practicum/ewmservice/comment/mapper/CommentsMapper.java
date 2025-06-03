package ru.practicum.ewmservice.comment.mapper;

import ru.practicum.ewmservice.comment.dto.CommentDto;
import ru.practicum.ewmservice.comment.dto.NewCommentDto;
import ru.practicum.ewmservice.comment.dto.UpdateCommentDto;
import ru.practicum.ewmservice.comment.model.Comment;

import java.time.LocalDateTime;

public class CommentsMapper {

    public static CommentDto toDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setText(comment.getText());
        commentDto.setCommentator(comment.getCommentator().getId());
        commentDto.setCommentTime(comment.getCommentTime());
        commentDto.setStatus(comment.getStatus());
        commentDto.setEvent(comment.getEvent());

        return commentDto;
    }

    public static Comment toEntity(NewCommentDto commentDto) {
        Comment comment = new Comment();
        comment.setText(commentDto.getText());
        //comment.setCommentator(commentDto.getCommentator());
        comment.setCommentTime(LocalDateTime.now());
        comment.setStatus(commentDto.getStatus());
        //comment.setEvent(commentDto.getEventId());

        return comment;
    }

    public static Comment toUpdatedEntity(UpdateCommentDto dto, Comment comment) {
        if (dto.getText() != null) {
            comment.setText(dto.getText());
        }
        if (dto.getStatus() != null) {
            comment.setStatus(dto.getStatus());
        }
        comment.setCommentTime(LocalDateTime.now());
        return comment;
    }


}
