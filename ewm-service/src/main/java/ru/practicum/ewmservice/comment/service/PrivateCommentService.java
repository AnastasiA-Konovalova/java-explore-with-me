package ru.practicum.ewmservice.comment.service;

import ru.practicum.ewmservice.comment.dto.CommentDto;
import ru.practicum.ewmservice.comment.dto.NewCommentDto;
import ru.practicum.ewmservice.comment.dto.UpdateCommentDto;

import java.util.List;

public interface PrivateCommentService {

    List<CommentDto> getCommentsByEventId(Long eventId, Long from, Long size);

    CommentDto getCommentById(Long commentId);

    List<CommentDto> getCommentsByUserId(Long userId);

    CommentDto saveComment(NewCommentDto newCommentDto, Long userId, Long eventId);

    CommentDto updateComment(UpdateCommentDto updateCommentDto, Long commentId, Long userId, Long eventId);

    void deleteCommentById(Long commentId, Long userId);
}