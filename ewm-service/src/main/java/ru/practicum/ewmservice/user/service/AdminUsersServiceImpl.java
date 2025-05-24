package ru.practicum.ewmservice.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewmservice.exception.NotFoundException;
import ru.practicum.ewmservice.exception.ValidationException;
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
    public List<UserDto> getUsersWithConditions(List<Integer> ids, Integer from, Integer size) {
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
        //uniqueEmailValidate(userDto);
        System.out.println(userDto.getName());

        User user = UserMapper.toEntity(userDto);
        System.out.println(user);
        return UserMapper.toDto(userRepository.save(user));
    }

    @Override
    public void delete(Integer userId) {
        getUserById(userId);
        userRepository.deleteById(userId);
    }


    private void uniqueEmailValidate(UserDto userDto) {
//        if (!usersRepository.getUserByEmail(userDto.getEmail()).isEmpty()) {
//            throw new ValidationException("Пользователь email " + userDto.getEmail() + " уже существует");
//        }

//        List<User> userList = usersRepository.findAll();
//        boolean emailExists = userList.stream()
//                .anyMatch(user -> userDto.getEmail().equals(user.getEmail()));
//        if (emailExists) {
//            throw new IllegalStateException("Пользователь email" + userDto.getEmail() + "уже существует");
//        }
    }

    public User getUserById(Integer userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователя с таким id " + userId + " не существует."));
    }
}
