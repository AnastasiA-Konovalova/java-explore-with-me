package ru.practicum.ewmservice.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.practicum.ewmservice.event.model.Event;

@Repository
public interface AdminEventsRepository extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {
}