package ru.practicum.ewmservice.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDto {

    private Long id;

    @Email(message = "Неверный формат email")
    @NotBlank(message = "Email не должен быть пустым")
    @Size(min = 6, max = 254)
    @Pattern(
            regexp = "^\\S+@\\S+\\.\\S+$",
            message = "Неверный формат email или слишком длинная часть до @ (макс 64 символа)"
    )
    private String email;

    @NotBlank(message = "Имя не должен быть пустым")
    @Size(min = 2, max = 250)
    private String name;
}