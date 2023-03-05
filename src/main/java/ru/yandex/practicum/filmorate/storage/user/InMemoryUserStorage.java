package ru.yandex.practicum.filmorate.storage.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validator.UserValidator;

import java.util.*;

@Component
@RequiredArgsConstructor
public class InMemoryUserStorage implements UserStorage {
    private final Map<Long, User> users = new HashMap<>();
    private long idUser = 0;

    @Override
    public List<User> getUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User createUser(User user) {
        UserValidator.isValidNameUsers(user);
        generateIdUsers();
        user.setId(idUser);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        if (users.containsKey(user.getId())) {
            UserValidator.isValidNameUsers(user);
            users.put(user.getId(), user);
        } else {
            throw new NotFoundException("Такого пользователя нет в базе.");
        }
        return user;
    }

    @Override
    public void clearUsers() {
        if (!users.isEmpty()) {
            users.clear();
        }
    }

    public void deleteUserById(long id) {
        if (!users.containsKey(id)) {
            throw new NotFoundException(String.format("Пользователь № %d не найден", id));
        }
        users.remove(id);
    }

    public User findUserById(long id) {
        if (!users.containsKey(id)) {
            throw new NotFoundException(String.format("Пользователь не найден", id));
        }
        return getUsers().stream()
                .filter(u -> u.getId() == id)
                .findFirst().orElseThrow(
                        () -> new NotFoundException(String.format("Пользователь № %d не найден", id)));
    }

    private void generateIdUsers() {
        ++idUser;
    }
}
