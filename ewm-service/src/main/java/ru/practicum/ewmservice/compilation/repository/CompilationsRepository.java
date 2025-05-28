package ru.practicum.ewmservice.compilation.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.ewmservice.category.model.Category;
import ru.practicum.ewmservice.compilation.dto.EventInCompilationDto;
import ru.practicum.ewmservice.compilation.model.Compilation;

import java.util.List;

@Repository
public interface CompilationsRepository extends JpaRepository<Compilation, Long> {

    Page<Compilation> findAllByPinned(Boolean pinned, Pageable pageRequest);

//    @Query(value = """
//    SELECT e.annotation AS annotation, ca.name AS name, e.confirmed_requests AS confirmedRequests,
//           e.event_date AS eventDate, e.initiator_id AS initiatorId, u.id AS userId,
//           u.name AS userName, e.paid AS paid, e.title AS title, e.views AS views
//    FROM compilations c
//    JOIN compilation_events ce ON c.id = ce.compilation_id
//    JOIN events e ON ce.event_id = e.id
//    JOIN categories ca ON e.category_id = ca.id
//    JOIN users u ON e.initiator_id = u.id
//    WHERE c.pinned = :pinned
//    ORDER BY e.id
//    LIMIT :size
//    OFFSET :from
//""", nativeQuery = true)
//    List<EventInCompilationDto> getListCompilationsByPinnedWithFromAndOrSize(
//            @Param("pinned") Boolean pinned,
//            @Param("from") Long from,
//            @Param("size") Long size
//    );


}