package ru.practicum.ewmservice.event.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.ewmservice.event.model.Event;

import java.util.List;

public interface PrivateEventsRepository extends JpaRepository<Event, Long> {

    @Query("SELECT e " +
            "FROM Event e " +
            "WHERE e.initiator.id = :userId " +
            "ORDER BY e.category.id")
    Page<Event> findAllByInitiatorId(@Param("userId") Long userId, Pageable pageable);

    List<Event> findAllByCategoryId(Long id);
}