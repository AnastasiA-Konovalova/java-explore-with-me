package ru.practicum.ewmservice.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.ewmservice.category.model.Category;
import ru.practicum.ewmservice.event.model.Event;
import ru.practicum.ewmservice.user.model.User;

import java.util.List;

@Repository
public interface PublicEventsRepository extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {

//    @Query(value = "SELECT e.annotation, e.description, " +
//            "FROM events e " +
//            "WHERE (:text IS NULL OR ILIKE '%annotation%' " +
//            "AND (:categories IS NULL OR e.id" +
//            "AND (:paid IS NULL OR e.paid = :paid) " +
//            "AND (:rangeStart IS NULL OR e.event_date >= CAST(rangeStart AS timestamp " +
//            "AND (:rangeEnd IS NULL OR e.event_date <= CAST(rangeEnd AS timestamp " +
//            "AND (:onlyAvailable IS NULL OR " +
//            "(:onlyAvailable = TRUE AND e.participant_limit > e.confirmed_requests " +
//            "OR :onlyAvailable = FALSE) " +
//            "ORDER BY " +
//            "CASE WHEN :sort = 'EVENT_DATE'" +
//            "AND (:sort = 'EVENT_DATE' " +
//            "WHERE (:ids IS NULL OR u.id IN (:ids)) " +
//            "ORDER BY u.id " +
//            "LIMIT :size " +
//            "OFFSET :from", nativeQuery = true)
//    List<User> getEventsWithConditions(
//            @Param("ids") List<Integer> ids,
//            @Param("from") Integer from,
//            @Param("size") Integer size
//    );

}
