package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Set;

public interface UserStorage {
    User create(User user);

    void delete(User user);

    User update(User user);

    List<User> getUsers();

    User find(Long id);

}
