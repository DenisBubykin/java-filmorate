package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.user.UserService;


import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
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

    @PutMapping(value = "/users/{id}/friends/{friendId}")
    public void addFriends(@PathVariable Long id, @PathVariable Long userId) {
        userService.addFriends(id, userId);
    }

    @DeleteMapping(value = "/users/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable Long id, @PathVariable Long userId) {
        userService.deleteFriend(id, userId);
    }

    @GetMapping("/users/{id}/friends")
    public List<User> findFriends(@PathVariable Long id) {
        return userService.getFriends(userService.getUserStorage().find(id).getFriends());
    }

    @GetMapping("/users/{id}/friends/common/{otherId}")
    public List<User> getCommonFriends(@PathVariable Set<Long> id, @PathVariable Set<Long> otherId) {
        return userService.addCommonFriends(id, otherId);
    }
}