package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.user.UserService;


import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")

public class UserController {

    private final UserService userService;

    public List<User> getUsers() {
        return userService.getUsers();
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) throws ValidationException {
        return userService.create(user);
    }

    @PutMapping(value = "/users/{id}/friends/{friendId}")
    public User update(@Valid @RequestBody User updateUser) {
        return userService.update(updateUser);
    }

    @GetMapping("/users/{id}")
    public User find(@PathVariable Long id) {
        return userService.getUserStorage().find(id);
    }

    @GetMapping("/users/{id}/friends")
    public List<User> findFriends(@PathVariable Long id) {
        return userService.getFriends(userService.getUserStorage().find(id).getFriends());
    }
}