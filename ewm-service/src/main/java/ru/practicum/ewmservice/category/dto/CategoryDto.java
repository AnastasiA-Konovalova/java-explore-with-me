package ru.practicum.ewmservice.category.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategoryDto {

    private Long id;

    //todo:
    @Size(min = 1, max = 50)
    private String name;
}