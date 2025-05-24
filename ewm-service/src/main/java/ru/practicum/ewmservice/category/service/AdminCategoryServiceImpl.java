package ru.practicum.ewmservice.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmservice.category.model.Category;
import ru.practicum.ewmservice.category.dto.CategoryDto;
import ru.practicum.ewmservice.category.dto.NewCategoryDto;
import ru.practicum.ewmservice.category.mapper.CategoryMapper;
import ru.practicum.ewmservice.category.repository.AdminCategoryRepository;
import ru.practicum.ewmservice.exception.NotFoundException;

@Service
@RequiredArgsConstructor
public class AdminCategoryServiceImpl implements AdminCategoryService {

    private final AdminCategoryRepository categoryRepository;

    @Transactional
    public CategoryDto saveNewCategory(NewCategoryDto newCategoryDto) {
        Category category = CategoryMapper.toEntity(newCategoryDto);
        return CategoryMapper.toDto(categoryRepository.save(category));
    }

    public void deleteById(Integer id) {
        checkCategoryExists(id);
        //todo проверить что с категорией не связаны события

        categoryRepository.deleteById(id);
    }

    public CategoryDto updateById(CategoryDto categoryDto, Integer catId) {
        checkCategoryExists(catId);
        Category category = CategoryMapper.toEntityCategoryUpdate(categoryDto);

        return CategoryMapper.toDto(categoryRepository.save(category));
    }

    private void checkCategoryExists(Integer id) {
        if (!categoryRepository.existsById(id)) {
            throw new NotFoundException("Категория с id " + id + " не найдена.");
        }
    }

}
