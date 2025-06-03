package ru.practicum.ewmservice.comment.service.specifications;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import ru.practicum.ewmservice.comment.model.Comment;
import ru.practicum.ewmservice.comment.model.CommentStatus;
import ru.practicum.ewmservice.event.model.Event;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CommentSpecifications {

    public static Specification<Comment> filterCommentConditionals(
            List<Long> users,
            List<Long> events,
            List<String> statuses,
            String rangeStart,
            String rangeEnd
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

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            if (rangeStart != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("commentTime"), LocalDateTime.parse(rangeStart, formatter)));
            }
//            else if (rangeStart == null && rangeEnd == null) {
//                predicates.add(criteriaBuilder.greaterThan(root.get("commentTime"), LocalDateTime.now()));
//            }

            if (rangeEnd != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("commentTime"), LocalDateTime.parse(rangeEnd, formatter)));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}