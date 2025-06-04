package ru.practicum.ewmservice.comment.service.specifications;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import ru.practicum.ewmservice.comment.model.Comment;
import ru.practicum.ewmservice.comment.model.CommentStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CommentSpecifications {

    public static Specification<Comment> filterCommentConditionals(
            List<Long> users,
            List<Long> events,
            List<String> statuses,
            LocalDateTime rangeStart,
            LocalDateTime rangeEnd
    ) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (users != null) {
                predicates.add(root.get("commentator").get("id").in(users));
            }

            if (events != null) {
                predicates.add(root.get("event").get("id").in(events));
            }

            if (statuses != null && !statuses.isEmpty()) {
                List<CommentStatus> enumStatuses = statuses.stream()
                        .map(String::toUpperCase)
                        .map(CommentStatus::valueOf)
                        .toList();
                predicates.add(root.get("status").in(enumStatuses));
            }

            if (rangeStart != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("commentTime"), rangeStart));
            }

            if (rangeEnd != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("commentTime"), rangeEnd));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}