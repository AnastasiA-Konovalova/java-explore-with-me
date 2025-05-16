package ru.practicum.stats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.dto.EndpointHitProjection;
import ru.practicum.stats.model.EndpointHit;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StatsRepository extends JpaRepository<EndpointHit, Long> {

    @Query("SELECT e.app, e.uri, COUNT(DISTINCT e.ip) AS hits " +
            "FROM EndpointHit e " +
            "WHERE e.timestamp BETWEEN :start AND :end " +
            "GROUP BY e.app, e.uri " +
            "ORDER BY hits DESC")
    List<EndpointHitProjection> findAllNotUrisUnique(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("SELECT e.app, e.uri, COUNT(DISTINCT e.ip) AS hits " +
            "FROM EndpointHit e " +
            "WHERE e.timestamp BETWEEN :start AND :end AND e.uri IN :uris " +
            "GROUP BY e.app, e.uri " +
            "ORDER BY hits DESC")
    List<EndpointHitProjection> findAllWithUrisUnique(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end,
                                                      @Param("uris") List<String> uris);

    @Query("SELECT e.app, e.uri, COUNT(e.id) AS hits " +
            "FROM EndpointHit e " +
            "WHERE e.timestamp BETWEEN :start AND :end AND e.uri IN :uris " +
            "GROUP BY e.app, e.uri " +
            "ORDER BY hits DESC")
    List<EndpointHitProjection> findAllWithUris(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end,
                                                @Param("uris") List<String> uris);

    @Query("SELECT e.app, e.uri, COUNT(e.id) AS hits " +
            "FROM EndpointHit e " +
            "WHERE e.timestamp BETWEEN :start AND :end " +
            "GROUP BY e.app, e.uri " +
            "ORDER BY hits DESC")
    List<EndpointHitProjection> findAllNotUris(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}