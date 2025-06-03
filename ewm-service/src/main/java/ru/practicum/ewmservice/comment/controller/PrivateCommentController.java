package ru.practicum.ewmservice.comment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewmservice.comment.dto.CommentDto;
import ru.practicum.ewmservice.comment.dto.NewCommentDto;
import ru.practicum.ewmservice.comment.dto.UpdateCommentDto;
import ru.practicum.ewmservice.comment.service.PrivateCommentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/comments")
public class PrivateCommentController {

    private final PrivateCommentService commentService;

    @GetMapping("/events/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public List<CommentDto> getCommentsByEventId(@PathVariable(name = "eventId") Long eventId,
                                                 @RequestParam(defaultValue = "0") Long from,
                                                 @RequestParam(defaultValue = "10") Long size) {
        return commentService.getCommentsByEventId(eventId, from, size);
    }

    @GetMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public CommentDto getCommentById(@PathVariable(name = "commentId") Long commentId) {
        return commentService.getCommentById(commentId);
    }

    @GetMapping("/users/{userId}/events/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public List<CommentDto> getCommentsByUserId(@PathVariable(name = "userId") Long userId) {
        return commentService.getCommentsByUserId(userId);
    }

    @PostMapping("/{userId}/events/{eventId}")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto saveComment(@Valid @RequestBody NewCommentDto newCommentDto,
                                  @PathVariable(name = "userId") Long userId,
                                  @PathVariable(name = "eventId") Long eventId) {
        return commentService.saveComment(newCommentDto, userId, eventId);
    }

    @PatchMapping("/{commentId}/users/{userId}/events/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public CommentDto updateComment(@Valid @RequestBody UpdateCommentDto updateCommentDto,
                                    @PathVariable(name = "commentId") Long commentId,
                                    @PathVariable(name = "userId") Long userId,
                                    @PathVariable(name = "eventId") Long eventId) {
        return commentService.updateComment(updateCommentDto, commentId, userId, eventId);
    }

    @DeleteMapping("/{commentId}/users/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCommentById(@PathVariable(name = "commentId") Long commentId,
                                  @PathVariable(name = "userId") Long userId) {
        commentService.deleteCommentById(commentId, userId);
    }
}