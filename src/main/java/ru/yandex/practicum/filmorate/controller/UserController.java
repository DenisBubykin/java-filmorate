package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;


import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private int nextId = 1;
    private HashMap<Long, User> users = new HashMap<>();

    private long getNextId() {
        return nextId++;
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) throws ValidationException {
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("ДР не может быть в будущем. ");
        }
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        log.info("POST / users request received");
        users.put(getNextId(), user);
        return user;
    }

    @PutMapping
    public User update(@Valid @RequestBody User updateUser) throws ValidationException {
        log.info("PUT /users request received");
        long updateId = updateUser.getId();
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

    @GetMapping
    public List<User> getUsers() {
        log.info("Get all users {}", users.size());
        List<User> list = new ArrayList<>(users.values());
        log.info("GET /users request done");
        return list;
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