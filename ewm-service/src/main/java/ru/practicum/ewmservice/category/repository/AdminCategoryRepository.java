package ru.practicum.ewmservice.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewmservice.category.dto.Category;

@Repository
public interface AdminCategoryRepository extends JpaRepository<Category, Integer> {
}
