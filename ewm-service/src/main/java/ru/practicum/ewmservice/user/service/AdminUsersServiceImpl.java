package ru.practicum.ewmservice.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewmservice.exception.ConflictException;
import ru.practicum.ewmservice.exception.NotFoundException;
import ru.practicum.ewmservice.user.dto.UserDto;
import ru.practicum.ewmservice.user.mapper.UserMapper;
import ru.practicum.ewmservice.user.model.User;
import ru.practicum.ewmservice.user.repository.AdminUsersRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminUsersServiceImpl implements AdminUsersService {

    private final AdminUsersRepository userRepository;

    @Override
    public List<UserDto> getUsersWithConditions(List<Long> ids, Long from, Long size) {
        List<User> users;
        if (ids == null || ids.isEmpty()) {
            users = userRepository.findAllWithPagination(from, size);
        } else {
            users = userRepository.getUsersByIdsWithSizeAndSkipFrom(ids, from, size);
        }

        if (users.isEmpty()) {
            return List.of();
        }
        return users.stream()
                .map(UserMapper::toDto)
                .toList();
    }

    @Override
    public UserDto saveUser(UserDto userDto) {
        uniqueEmailValidate(userDto);
        User user = UserMapper.toEntity(userDto);
        return UserMapper.toDto(userRepository.save(user));
    }

    @Override
    public void delete(Long userId) {
        getUserById(userId);
        userRepository.deleteById(userId);
    }

    private void uniqueEmailValidate(UserDto userDto) {
        User user = userRepository.findByEmail(userDto.getEmail());
        if (user != null) {
            throw new ConflictException("Пользователь email " + userDto.getEmail() + " уже существует");
        }
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователя с таким id " + userId + " не существует."));
    }
}