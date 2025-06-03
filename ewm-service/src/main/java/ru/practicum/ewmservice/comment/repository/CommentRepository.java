package ru.practicum.ewmservice.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.ewmservice.comment.model.Comment;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>, JpaSpecificationExecutor<Comment> {

    @Query(value = "SELECT * " +
            "FROM comments c " +
            "WHERE c.event_id = :eventId " +
            "ORDER BY c.id " +
            "LIMIT :size " +
            "OFFSET :from", nativeQuery = true)
    List<Comment> getByEventIdBySizeAndSkipFrom(
            @Param("eventId") Long eventId,
            @Param("from") Long from,
            @Param("size") Long size
    );

    //List<Comment> findAllByEventId(Long eventId);

    List<Comment> findAllByCommentatorId(Long userId);
}