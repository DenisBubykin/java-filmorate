package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.user.UserService;


import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    private int nextId = 1;
    private HashMap<Integer, User> users = new HashMap<>();

    private int getNextId() {
        return nextId++;
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        return userService.getUserStorage().create(user);
    }

    @PutMapping
    public User update(@Valid @RequestBody User updateUser) {
        return userService.getUserStorage().update(updateUser);
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.getUserStorage().getUsers();
    }

    @GetMapping("/users/{id}")
    public User find(@PathVariable Long id) {
        return userService.getUserStorage().find(id);
    }

    @GetMapping("/users/{id}/friends")
    public List<User> findFriends(@PathVariable Long id) {
        return userService.getFriends(userService.getUserStorage().find(id).getFriends());
    }

    @PutMapping("/users/{id}/friends/{friendId}")
    public void update(@PathVariable Long id, @PathVariable Long friendId) {
        userService.addFriends(id, friendId);
    }
}