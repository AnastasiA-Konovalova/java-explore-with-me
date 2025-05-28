package ru.practicum.ewmservice.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmservice.category.model.Category;
import ru.practicum.ewmservice.category.dto.CategoryDto;
import ru.practicum.ewmservice.category.dto.NewCategoryDto;
import ru.practicum.ewmservice.category.mapper.CategoryMapper;
import ru.practicum.ewmservice.category.repository.CategoriesRepository;
import ru.practicum.ewmservice.exception.NotFoundException;

@Service
@RequiredArgsConstructor
public class AdminCategoryServiceImpl implements AdminCategoryService {

    private final CategoriesRepository categoryRepository;
    private final PublicCategoriesService categoriesService;

    @Transactional
    public CategoryDto saveNewCategory(NewCategoryDto newCategoryDto) {
        Category category = CategoryMapper.toEntity(newCategoryDto);
        return CategoryMapper.toDto(categoryRepository.save(category));
    }

    public void deleteById(Long id) {
        checkCategoryExists(id);
        //todo проверить что с категорией не связаны события
        System.out.println(id);
        categoryRepository.deleteById(id);
    }

    public CategoryDto updateById(CategoryDto categoryDto, Long catId) {
        Category category = checkCategoryExists(catId);

        Category categoryUpdate = CategoryMapper.toEntityCategoryUpdate(categoryDto, category);

        return CategoryMapper.toDto(categoryRepository.save(categoryUpdate));
    }

    private Category checkCategoryExists(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("Категория с id " + id + " не найдена."));
    }

}
