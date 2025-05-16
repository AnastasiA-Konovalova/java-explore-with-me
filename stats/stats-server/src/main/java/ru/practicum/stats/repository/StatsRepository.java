package ru.practicum.stats.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.stats.model.EndpointHit;
import ru.practicum.stats.projection.ViewStatsProjection;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StatsRepository extends org.springframework.data.jpa.repository.JpaRepository<EndpointHit, Integer> {
    @Query(value = "SELECT app, uri, COUNT(*) AS hits " +
            "FROM endpoint_hits e " +
            "WHERE e.act_time BETWEEN :start AND :end " +
            "GROUP BY e.app, e.uri " +
            "ORDER BY COUNT(e.ip) DESC", nativeQuery = true)
    List<ViewStatsProjection> findAllNotUrisStats(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );

    @Query(value = "SELECT app, uri, COUNT(DISTINCT e.ip) AS hits " +
            "FROM endpoint_hits e " +
            "WHERE e.act_time BETWEEN :start AND :end " +
            "GROUP BY e.app, e.uri " +
            "ORDER BY hits DESC", nativeQuery = true)
    List<ViewStatsProjection> findAllNotUrisStatsUnique(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );

    @Query(value = "SELECT app, uri, COUNT(*) AS hits " +
            "FROM endpoint_hits e " +
            "WHERE e.act_time BETWEEN :start AND :end " +
            "AND (:uris IS NULL OR e.uri IN :uris) " +
            "GROUP BY e.app, e.uri " +
            "ORDER BY hits DESC", nativeQuery = true)
    List<ViewStatsProjection> findAllStats(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            @Param("uris") List<String> uris
    );

    @Query(value = "SELECT app, uri, COUNT(DISTINCT e.ip) AS hits " +
            "FROM endpoint_hits e " +
            "WHERE e.act_time BETWEEN :start AND :end " +
            "AND (:uris IS NULL OR e.uri IN :uris) " +
            "GROUP BY e.app, e.uri " +
            "ORDER BY hits DESC", nativeQuery = true)
    List<ViewStatsProjection> findAllStatsUnique(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            @Param("uris") List<String> uris
    );
}