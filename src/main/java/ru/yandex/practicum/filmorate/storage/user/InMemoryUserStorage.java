package ru.yandex.practicum.filmorate.storage.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Component
public class InMemoryUserStorage implements UserStorage {

    private final HashMap<Integer, User> users = new HashMap<>();
    private int nextId = 1;
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserStorage.class);

    private int getNextId() {
        return nextId++;
    }

    @Override
    public User create(User user) {
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("ДР не может быть в будущем. ");
        }
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        log.info("POST / users request received");
        int userId = getNextId();
        user.setId(userId);
        users.put(userId, user);
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
    public User update(User updateUser) {
        log.info("PUT /users request received");
        int updateId = updateUser.getId();
        if (users.containsKey(updateId)) {
            if (isValid(updateUser)) {
                users.put(updateId, updateUser);
            } else {
                log.error("Request PUT /users contains invalid data");
                throw new ValidationException("Update user date is not valid");
            }
        } else {
            log.error("Request PUT /users contains invalid id");
            throw new ValidationException("Update user id is not valid");
        }
        log.info("PUT /users request done");
        return updateUser;
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

    public boolean isValid(User user){
        LocalDate validBirthday = LocalDate.now();
        boolean result = false;
        if (!user.getEmail().isEmpty() && user.getEmail().contains("@")) {
            if (!user.getLogin().isEmpty() && !user.getLogin().contains(" ")) {
                if (user.getBirthday().isBefore(validBirthday)) {
                    result = true;
                }
            }
        }
        return result;
    }

}
