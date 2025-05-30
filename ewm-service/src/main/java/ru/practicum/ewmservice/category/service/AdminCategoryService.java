package ru.practicum.ewmservice.category.service;

import ru.practicum.ewmservice.category.dto.CategoryDto;
import ru.practicum.ewmservice.category.dto.NewCategoryDto;
import ru.practicum.ewmservice.category.model.Category;

public interface AdminCategoryService {

    CategoryDto saveNewCategory(NewCategoryDto newCategoryDto);

    void deleteById(Long id);

    CategoryDto updateById(CategoryDto categoryDto, Long catId);

    Category checkCategoryExists(Long id);
}