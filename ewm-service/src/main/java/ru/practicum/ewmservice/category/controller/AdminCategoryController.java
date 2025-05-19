package ru.practicum.ewmservice.category.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewmservice.category.dto.CategoryDto;
import ru.practicum.ewmservice.category.dto.NewCategoryDto;
import ru.practicum.ewmservice.category.service.AdminCategoryService;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin")
public class AdminCategoryController {

    private final AdminCategoryService categoryService;

    @PostMapping("/categories")
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto saveNewCategory(@Valid @RequestBody NewCategoryDto nawCategoryDto) {
        // todo: unique in table
        //todo: ошибки
        return categoryService.saveNewCategory(nawCategoryDto);
    }
}
