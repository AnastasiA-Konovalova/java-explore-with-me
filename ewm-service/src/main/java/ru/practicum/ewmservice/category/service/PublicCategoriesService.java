package ru.practicum.ewmservice.category.service;

import ru.practicum.ewmservice.category.dto.CategoryDto;

import java.util.List;

public interface PublicCategoriesService {

    List<CategoryDto> get(Integer from, Integer size);

    CategoryDto getById(Integer catId);
}