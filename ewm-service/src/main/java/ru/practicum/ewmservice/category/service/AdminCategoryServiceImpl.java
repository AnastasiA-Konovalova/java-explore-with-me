package ru.practicum.ewmservice.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmservice.category.dto.CategoryDto;
import ru.practicum.ewmservice.category.dto.NewCategoryDto;
import ru.practicum.ewmservice.category.mapper.CategoryMapper;
import ru.practicum.ewmservice.category.model.Category;
import ru.practicum.ewmservice.category.repository.CategoriesRepository;
import ru.practicum.ewmservice.event.model.Event;
import ru.practicum.ewmservice.event.repository.PrivateEventsRepository;
import ru.practicum.ewmservice.exception.ConflictException;
import ru.practicum.ewmservice.exception.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminCategoryServiceImpl implements AdminCategoryService {

    private final CategoriesRepository categoryRepository;
    private final PrivateEventsRepository eventsRepository;

    @Transactional
    public CategoryDto saveNewCategory(NewCategoryDto newCategoryDto) {
        Category category = CategoryMapper.toEntity(newCategoryDto);
        Category existing = categoryRepository.findByName(category.getName());
        if (existing != null) {
            throw new ConflictException("Категория с именем " + category.getName() + " уже существует.");
        }
        return CategoryMapper.toDto(categoryRepository.save(category));
    }

    public void deleteById(Long id) {
        checkCategoryExists(id);
        List<Event> eventList = eventsRepository.findAllByCategoryId(id);
        if (!eventList.isEmpty()) {
            String events = eventList.stream()
                    .limit(5)
                    .map(Event::toString)
                    .collect(Collectors.joining(", "));

            if (eventList.size() > 5) {
                events += " и другие.";
            }
            throw new ConflictException("Категория с id " + id + " не должна быть связана с существующим событием. " +
                    "События: " + events);
        }
        categoryRepository.deleteById(id);
    }

    public CategoryDto updateById(CategoryDto categoryDto, Long catId) {
        checkCategoryUpdate(categoryDto, catId);
        Category categoryUpdate = CategoryMapper.toEntityCategoryUpdate(categoryDto);

        return CategoryMapper.toDto(categoryRepository.save(categoryUpdate));
    }

    public Category checkCategoryExists(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Категория с id " + id + " не найдена."));
    }

    private Category checkCategoryUpdate(CategoryDto categoryDto, Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Категория с id " + id + " не найдена."));

        Category existing = categoryRepository.findByName(categoryDto.getName());
        if (existing != null && !existing.getId().equals(id)) {
            throw new ConflictException("Категория с именем " + category.getName() + " уже существует.");
        }

        return category;
    }
}