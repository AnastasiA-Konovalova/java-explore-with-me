package ru.practicum.ewmservice.event.service.specifications;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import ru.practicum.ewmservice.event.model.Event;
import ru.practicum.ewmservice.event.model.State;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class EventSpecifications {

    public static Specification<Event> filterEventConditionals(
            String text,
            List<Integer> categories,
            Boolean paid,
            String rangeStart,
            String rangeEnd,
            Boolean onlyAvailable
    ) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(criteriaBuilder.equal(root.get("state"), State.PUBLISHED));

            if (text != null) {
                Predicate annotationPr = criteriaBuilder.like(criteriaBuilder.lower(root.get("annotation")),
                        "%" + text.toLowerCase() + "%");
                Predicate descriptionPr = criteriaBuilder.like(criteriaBuilder.lower(root.get("description")),
                        "%" + text.toLowerCase() + "%");
                predicates.add(criteriaBuilder.or(annotationPr, descriptionPr));
            }

            if (categories != null) {
                predicates.add(root.get("category").get("id").in(categories));
            }

            if (paid != null) {
                predicates.add(criteriaBuilder.equal(root.get("paid"), paid));
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            if (rangeStart != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("eventDate"), LocalDateTime.parse(rangeStart, formatter)));
            } else if (rangeStart == null && rangeEnd == null) {
                predicates.add(criteriaBuilder.greaterThan(root.get("eventDate"), LocalDateTime.now()));
            }

            if (rangeEnd != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("eventDate"), LocalDateTime.parse(rangeEnd, formatter)));
            }

            if (onlyAvailable != null && onlyAvailable) {
                predicates.add(criteriaBuilder.or(criteriaBuilder.isNull(root.get("participantLimit")), criteriaBuilder.greaterThan(root.get("participantLimit"), root.get("confirmedRequests"))));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
