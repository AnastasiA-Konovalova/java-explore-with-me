package ru.practicum.ewmservice.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.ewmservice.user.model.User;

import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT * " +
            "FROM users u " +
            "WHERE (:ids IS NULL OR u.id IN (:ids)) " +
            "ORDER BY u.id " +
            "LIMIT :size " +
            "OFFSET :from", nativeQuery = true)
    List<User> getUsersByIdsWithSizeAndSkipFrom(
            @Param("ids") List<Long> ids,
            @Param("from") Long from,
            @Param("size") Long size
    );

    @Query(value = "SELECT * " +
            "FROM users " +
            "ORDER BY id " +
            "LIMIT :size " +
            "OFFSET :from", nativeQuery = true)
    List<User> findAllWithPagination(
            @Param("from") Long from,
            @Param("size") Long size
    );

    User findByEmail(String email);
}