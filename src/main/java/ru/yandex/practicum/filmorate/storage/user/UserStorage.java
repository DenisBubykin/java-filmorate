package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Set;

public interface UserStorage {
    User create(User user);

    void delete(User user);

    User update(User user);


    User find(Long id);

    List<User> getUsersByIds(Set<Long> ids);

    List<User> findAll();
}
