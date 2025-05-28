package ru.practicum.ewmservice.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.ewmservice.request.model.Request;

import java.util.List;

public interface PrivateEventsRequestRepository extends JpaRepository<Request, Long> {

    @Query(value = "SELECT * " +
            "FROM requests r " +
            "WHERE r.requester_id = :requesterId " +
            "ORDER BY r.id", nativeQuery = true)
    List<Request> getByUserId(@Param("requesterId") Long userId);

    List<Request> findAllByEventId(Long eventId);

    boolean existsByRequesterIdAndEventId(Long userId, Long eventId);
}


