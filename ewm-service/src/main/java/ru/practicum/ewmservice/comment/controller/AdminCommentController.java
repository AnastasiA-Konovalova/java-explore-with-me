package ru.practicum.ewmservice.comment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewmservice.comment.dto.CommentDto;
import ru.practicum.ewmservice.comment.model.CommentStatus;
import ru.practicum.ewmservice.comment.service.AdminCommentService;
import ru.practicum.ewmservice.comment.service.PrivateCommentService;
import ru.practicum.ewmservice.event.dto.EventFullDto;
import ru.practicum.ewmservice.event.model.Event;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/comments")
public class AdminCommentController {

    private final AdminCommentService commentService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CommentDto> getCommentsByConditions(@RequestParam(required = false) List<Long> users,
                                                    @RequestParam(required = false) List<Long> events,
                                                    @RequestParam(required = false) List<String> status,
                                                    @RequestParam(required = false) String rangeStart,
                                                    @RequestParam(required = false) String rangeEnd,
                                                    @RequestParam(defaultValue = "0") Long from,
                                                    @RequestParam(defaultValue = "10") Long size) {
        return commentService.getCommentsByConditions(users, events, status, rangeStart, rangeEnd, from, size);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCommentById(@PathVariable Long commentId) {
        commentService.deleteCommentById(commentId);
    }
}
