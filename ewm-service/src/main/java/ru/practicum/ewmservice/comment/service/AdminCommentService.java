package ru.practicum.ewmservice.comment.service;

import ru.practicum.ewmservice.comment.dto.CommentDto;
import ru.practicum.ewmservice.comment.dto.CommentDtoRequest;

import java.util.List;

public interface AdminCommentService {

    List<CommentDto> getCommentsByConditions(CommentDtoRequest commentDtoRequest, Long from, Long size);

    void deleteCommentById(Long commentId);
}