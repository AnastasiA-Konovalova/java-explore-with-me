package ru.practicum.ewmservice.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Setter
@Getter
public class UserDto {

    private Integer id;

    @Email(message = "Неверный формат email")
    @NotBlank(message = "Email не должен быть пустым")
    @Size(min = 6, max = 254)
    @Pattern(
            regexp = "^(?=.{1,64}@)[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
            message = "Неверный формат email или слишком длинная часть до @ (макс 64 символа)"
    )
    private String email;

    @NotBlank(message = "Имя не должен быть пустым")
    @Size(min = 2, max = 250)
    private String name;
}

//^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$
