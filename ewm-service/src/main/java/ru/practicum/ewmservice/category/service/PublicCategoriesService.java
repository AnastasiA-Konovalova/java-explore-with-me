package ru.practicum.ewmservice.category.service;

import ru.practicum.ewmservice.category.dto.CategoryDto;

import java.util.List;

public interface PublicCategoriesService {

    List<CategoryDto> get(Long from, Long size);

    CategoryDto getById(Long catId);
}