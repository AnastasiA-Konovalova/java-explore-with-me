package ru.practicum.ewmservice.event.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.ewmservice.event.model.Event;

import java.util.List;

public interface PrivateEventsRepository extends JpaRepository<Event, Long> {

//    @Query(value = "SELECT e.annotation, c.name, e.confirmed_requests, e.event_date, e.id, u.id, u.name, e.paid, " +
//            "e.title, e.views " +
//            "FROM events e " +
//            "JOIN categories c ON e.category_id = c.id " +
//            "JOIN users u ON e.initiator_id = u.id " +
//            "WHERE e.initiator_id = :userId " +
//            "ORDER BY c.id " +
//            "LIMIT :size " +
//            "OFFSET :from", nativeQuery = true)
//    List<Event> getEventByUserIdWithFromAndSize(
//            @Param("userId") Long userId,
//            @Param("from") Long from,
//            @Param("size") Long size
//    );

    @Query("SELECT e " +
            "FROM Event e " +
            "WHERE e.initiator.id = :userId " +
            "ORDER BY e.category.id")
    Page<Event> findAllByInitiatorId(@Param("userId") Long userId, Pageable pageable);


//    @Query(value = "SELECT c.id, c.name " +
//            "FROM categories c " +
//            "ORDER BY c.id " +
//            "LIMIT :size " +
//            "OFFSET :from", nativeQuery = true)
//    List<Category> getNameWithSizeAndSkipFrom(
//            @Param("from") Integer from,
//            @Param("size") Integer size
//    );
}
