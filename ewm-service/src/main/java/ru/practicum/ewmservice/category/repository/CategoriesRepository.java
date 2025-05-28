package ru.practicum.ewmservice.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.ewmservice.category.model.Category;

import java.util.List;

@Repository
public interface CategoriesRepository extends JpaRepository<Category, Long> {

    @Query(value = "SELECT c.id, c.name " +
            "FROM categories c " +
            "ORDER BY c.id " +
            "LIMIT :size " +
            "OFFSET :from", nativeQuery = true)
    List<Category> getNameWithSizeAndSkipFrom(
            @Param("from") Long from,
            @Param("size") Long size
    );

    Category findByName(String name);
}