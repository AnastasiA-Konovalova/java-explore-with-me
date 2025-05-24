package ru.practicum.ewmservice.user.service;

import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.ewmservice.user.dto.UserDto;
import ru.practicum.ewmservice.user.model.User;

import java.util.List;

public interface AdminUsersService {

    List<UserDto> getUsersWithConditions(List<Integer> ids, Integer from, Integer size);

    UserDto saveUser(UserDto userDto);

    void delete(Integer userId);

    User getUserById(Integer userId);
}
