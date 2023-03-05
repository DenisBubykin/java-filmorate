package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.user.UserService;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getUsers() {
        log.debug("Список пользователей");
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    public void getUserById(@PathVariable long id) {
        log.debug("Получили пользователя с id: {}", id);
        userService.findUserById(id);
    }

    @PostMapping()
    public User createUser(@RequestBody User user) {
        log.debug("Добавили: {}", user);
        return userService.createUser(user);
    }

    @PutMapping()
    public User updateUser(@RequestBody User user) {
        log.debug("Обновили: {}", user);
        return userService.updateUser(user);
    }

    @DeleteMapping
    public void clearUsers() {
        log.debug("Очистили список пользователей:");
        userService.clearUsers();
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable long id) {
        log.debug("Удалили пользователя по id: {}", id);
        userService.deleteUserById(id);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable long id, @PathVariable long friendId) {
        log.debug("Добавили пользователя с id {}, в друзья к пользователю с id {}", friendId, id);
        userService.addFriendsForUsers(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable long id, @PathVariable long friendId) {
        log.debug("Удалили пользователя с id {}, из друзей пользователя с id {}", friendId, id);
        userService.deleteFriendsForUsers(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public List<User> getFriendsUser(@PathVariable long id) {
        log.debug("Список друзей пользователя с id: {}", id);
        return userService.getFriendsUser(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getUsers(@PathVariable long id, @PathVariable long otherId) {
        log.debug("Список общих друзей пользователей: {}, {}", id, otherId);
        return userService.getListFriends(id, otherId);
    }
}