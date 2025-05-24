package ru.practicum.ewmservice.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewmservice.event.model.Event;

public interface PrivateEventsRepository extends JpaRepository<Event, Integer> {
}
