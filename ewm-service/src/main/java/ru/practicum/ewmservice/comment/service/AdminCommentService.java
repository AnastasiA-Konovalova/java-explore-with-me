package ru.practicum.ewmservice.comment.service;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.ewmservice.comment.dto.CommentDto;
import ru.practicum.ewmservice.comment.model.CommentStatus;
import ru.practicum.ewmservice.event.model.Event;

import java.util.List;

public interface AdminCommentService {

    List<CommentDto> getCommentsByConditions(List<Long> users, List<Long> events, List<String> status,
                                             String rangeStart, String rangeEnd, Long from, Long size);

    void deleteCommentById(Long commentId);
}
