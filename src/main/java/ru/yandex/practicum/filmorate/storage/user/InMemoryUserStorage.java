package ru.yandex.practicum.filmorate.storage.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Component
public class InMemoryUserStorage implements UserStorage {

    private final HashMap<Integer, User> users = new HashMap<>();
    private int id = 1;
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserStorage.class);

    @Override
    public User create(User user) {
        user.setName(checkAndReturnName(user));
        user.setId(id);
        users.put(id, user);
        id++;
        log.debug("Добавлен пользователь: {}", user);
        return user;
    }

    private String checkAndReturnName(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            return user.getLogin();
        } else {
            return user.getName();
        }
    }

    @Override
    public void delete(User user) {
        user.setName(checkAndReturnName(user));
        if (users.containsKey(user.getId())) {
            users.remove(user.getId());
            log.debug("Пользователь {} удалён", user);
        } else {
            throw new ValidationException("Пользователя с таким Id нет");
        }
    }

    @Override
    public User update(User user) {
        User oldUser;
        user.setName(checkAndReturnName(user));
        if (users.containsKey(user.getId())) {
            oldUser = users.get(user.getId());
            users.put(user.getId(), user);
            log.debug("Пользователь {} изменен на {}", oldUser, user);
        } else {
            throw new ValidationException("Пользователя с таким Id нет");
        }
        return user;
    }

    @Override
    public List<User> getUsers() {
        List<User> userList = new ArrayList<>(users.values());
        log.debug("Текущее количесвто пользователей: {}", users.size());
        return userList;
    }

    @Override
    public User find(Long id) {
        if (users.containsKey(id)) {
            return users.get(id);
        } else {
            throw new ValidationException("неверный номер ID");
        }
    }

    @Override
    public List<User> getUsersByIds(Set<Long> ids) {
        List<User> userList = new ArrayList<>();
        for (Long id : ids) {
            if (users.containsKey(id)) {
                userList.add(users.get(id));
            }
        }
        return userList;
    }

}

