package ru.practicum.ewmservice.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewmservice.category.dto.CategoryDto;
import ru.practicum.ewmservice.category.mapper.CategoryMapper;
import ru.practicum.ewmservice.category.model.Category;
import ru.practicum.ewmservice.category.repository.CategoriesRepository;
import ru.practicum.ewmservice.exception.NotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicCategoriesServiceImpl implements PublicCategoriesService {

    private final CategoriesRepository categoriesRepository;

    public List<CategoryDto> get(Long from, Long size) {
        List<Category> categories = categoriesRepository.getNameWithSizeAndSkipFrom(from, size);
        if (categories.isEmpty()) {
            return List.of();
        }
        return categories.stream()
                .map(CategoryMapper::toDto)
                .toList();
    }

    public CategoryDto getById(Long catId) {
        Category category = categoriesRepository.findById(catId)
                .orElseThrow(() -> new NotFoundException("Категория с id " + catId + " не найдена."));

        return CategoryMapper.toDto(category);
    }
}