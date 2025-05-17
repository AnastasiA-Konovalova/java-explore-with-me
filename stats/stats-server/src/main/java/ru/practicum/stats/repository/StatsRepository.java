package ru.practicum.stats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.stats.model.EndpointHit;
import ru.practicum.stats.projection.ViewStatsProjection;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StatsRepository extends JpaRepository<EndpointHit, Integer> {
    @Query(value = "SELECT a.name, e.uri, COUNT(*) AS hits " +
            "FROM endpoint_hits e " +
            "JOIN apps a ON e.app = a.id " +
            "WHERE e.act_time BETWEEN :start AND :end " +
            "GROUP BY a.name, e.uri " +
            "ORDER BY hits DESC", nativeQuery = true)
    List<ViewStatsProjection> findAllNotUrisStats(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );

    @Query(value = "SELECT a.name, e.uri, COUNT(DISTINCT e.ip) AS hits " +
            "FROM endpoint_hits e " +
            "JOIN apps a ON e.app = a.id " +
            "WHERE e.act_time BETWEEN :start AND :end " +
            "GROUP BY a.name, e.uri " +
            "ORDER BY hits DESC", nativeQuery = true)
    List<ViewStatsProjection> findAllNotUrisStatsUnique(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );

    @Query(value = "SELECT a.name, e.uri, COUNT(*) AS hits " +
            "FROM endpoint_hits e " +
            "JOIN apps a ON e.app = a.id " +
            "WHERE e.act_time BETWEEN :start AND :end " +
            "AND (:uris IS NULL OR e.uri IN :uris) " +
            "GROUP BY a.name, e.uri " +
            "ORDER BY hits DESC", nativeQuery = true)
    List<ViewStatsProjection> findAllStats(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            @Param("uris") List<String> uris
    );

    @Query(value = "SELECT a.name, e.uri, COUNT(DISTINCT e.ip) AS hits " +
            "FROM endpoint_hits e " +
            "JOIN apps a ON e.app = a.id " +
            "WHERE e.act_time BETWEEN :start AND :end " +
            "AND (:uris IS NULL OR e.uri IN :uris) " +
            "GROUP BY a.name, e.uri " +
            "ORDER BY hits DESC", nativeQuery = true)
    List<ViewStatsProjection> findAllStatsUnique(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            @Param("uris") List<String> uris
    );
}