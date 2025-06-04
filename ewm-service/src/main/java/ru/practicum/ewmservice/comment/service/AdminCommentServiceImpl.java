package ru.practicum.ewmservice.comment.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.practicum.ewmservice.comment.dto.CommentDto;
import ru.practicum.ewmservice.comment.dto.CommentDtoRequest;
import ru.practicum.ewmservice.comment.mapper.CommentsMapper;
import ru.practicum.ewmservice.comment.model.Comment;
import ru.practicum.ewmservice.comment.repository.CommentRepository;
import ru.practicum.ewmservice.comment.service.specifications.CommentSpecifications;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AdminCommentServiceImpl implements AdminCommentService {

    private final CommentRepository commentRepository;
    private final PrivateCommentService privateCommentService;

    @Override
    public List<CommentDto> getCommentsByConditions(CommentDtoRequest commentDtoRequest, Long from, Long size) {
        Specification<Comment> specification = CommentSpecifications.filterCommentConditionals(
                commentDtoRequest.getUsers(), commentDtoRequest.getEvents(), commentDtoRequest.getStatus(),
                commentDtoRequest.getRangeStart(), commentDtoRequest.getRangeEnd()
        );
        PageRequest pageRequest = PageRequest.of((int) (from / size), size.intValue());
        Page<Comment> commentPage = commentRepository.findAll(specification, pageRequest);
        if (commentPage.isEmpty()) {
            return List.of();
        }

        return commentPage.getContent().stream()
                .map(CommentsMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCommentById(Long commentId) {
        privateCommentService.getCommentById(commentId);
        commentRepository.deleteById(commentId);
    }
}