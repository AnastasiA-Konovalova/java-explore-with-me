package ru.practicum.ewmservice.category.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategoryDto {

    private Integer id;

    //todo:
    //maxLength:50 minLength: 1
    private String name;
}