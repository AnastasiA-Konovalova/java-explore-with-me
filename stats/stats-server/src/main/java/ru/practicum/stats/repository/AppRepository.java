package ru.practicum.stats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.stats.model.App;

import java.util.Optional;

@Repository
public interface AppRepository extends JpaRepository<App, Integer> {

    Optional<App> findByName(String name);
}
