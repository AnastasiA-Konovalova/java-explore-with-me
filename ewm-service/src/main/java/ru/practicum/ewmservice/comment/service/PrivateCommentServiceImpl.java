package ru.practicum.ewmservice.comment.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewmservice.comment.dto.CommentDto;
import ru.practicum.ewmservice.comment.dto.NewCommentDto;
import ru.practicum.ewmservice.comment.dto.UpdateCommentDto;
import ru.practicum.ewmservice.comment.mapper.CommentsMapper;
import ru.practicum.ewmservice.comment.model.Comment;
import ru.practicum.ewmservice.comment.repository.CommentRepository;
import ru.practicum.ewmservice.event.enums.State;
import ru.practicum.ewmservice.event.model.Event;
import ru.practicum.ewmservice.event.service.PrivateEventsService;
import ru.practicum.ewmservice.exception.ConflictException;
import ru.practicum.ewmservice.exception.NotFoundException;
import ru.practicum.ewmservice.user.model.User;
import ru.practicum.ewmservice.user.service.AdminUsersService;

import java.util.List;

@Service
@AllArgsConstructor
public class PrivateCommentServiceImpl implements PrivateCommentService {

    private final CommentRepository commentRepository;
    private final AdminUsersService usersService;
    private final PrivateEventsService eventsService;

    @Override
    public List<CommentDto> getCommentsByEventId(Long eventId, Long from, Long size) {
        eventsService.getById(eventId);
        List<Comment> comments = commentRepository.getByEventIdBySizeAndSkipFrom(eventId, from, size);
        return comments.stream()
                .map(CommentsMapper::toDto)
                .toList();
    }

    @Override
    public CommentDto getCommentById(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Комментарий с таким id " + commentId + " не найден."));
        return CommentsMapper.toDto(comment);
    }

    @Override
    public List<CommentDto> getCommentsByUserId(Long userId) {
        usersService.getUserById(userId);
        List<Comment> comments = commentRepository.findAllByCommentatorId(userId);
        return comments.stream()
                .map(CommentsMapper::toDto)
                .toList();
    }

    @Override
    public CommentDto saveComment(NewCommentDto newCommentDto, Long userId, Long eventId) {
        User user = usersService.getUserById(userId);
        Event event = eventsService.getById(eventId);
        checkConditions(eventId);

        Comment comment = CommentsMapper.toEntity(newCommentDto);
        comment.setEvent(event);
        comment.setCommentator(user);
        Comment update = commentRepository.save(comment);
        return CommentsMapper.toDto(update);
    }

    @Override
    public CommentDto updateComment(UpdateCommentDto updateCommentDto, Long commentId, Long userId, Long eventId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Комментарий с id " + commentId + " не найден"));
        usersService.getUserById(userId);
        Event event = eventsService.getById(eventId);
        checkCommentOwner(commentId, userId);
        checkConditions(eventId);
        CommentsMapper.toUpdatedEntity(updateCommentDto, comment);
        comment.setEvent(event);

        return CommentsMapper.toDto(comment);
    }

    @Override
    public void deleteCommentById(Long commentId, Long userId) {
        Long commentatorId = getCommentById(commentId).getCommentator();
        usersService.getUserById(userId);
        checkCommentOwner(commentatorId, userId);

        commentRepository.deleteById(commentId);
    }

    private void checkConditions(Long eventId) {
        Event event = eventsService.getById(eventId);
        if (!event.getState().equals(State.PUBLISHED)) {
            throw new ConflictException("Нельзя писать комментарий к еще не опубликованному событию.");
        }
    }

    private void checkCommentOwner(Long commentatorId, Long userId) {
        if (!commentatorId.equals(userId)) {
            throw new ConflictException("Пользователь с id " + userId + " не является автором комментария.");
        }
    }
}