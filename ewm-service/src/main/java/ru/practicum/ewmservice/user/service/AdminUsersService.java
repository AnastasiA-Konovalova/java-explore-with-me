package ru.practicum.ewmservice.user.service;

import ru.practicum.ewmservice.user.dto.UserDto;
import ru.practicum.ewmservice.user.model.User;

import java.util.List;

public interface AdminUsersService {

    List<UserDto> getUsersWithConditions(List<Long> ids, Long from, Long size);

    UserDto saveUser(UserDto userDto);

    void delete(Long userId);

    User getUserById(Long userId);
}