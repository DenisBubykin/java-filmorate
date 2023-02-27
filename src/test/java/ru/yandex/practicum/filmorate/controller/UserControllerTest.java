package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserControllerTest {

    @Autowired
    private UserController uController;
    @Autowired
    private UserStorage userStorage;
    private static String name;
    private static String login;
    private static String email;
    private static LocalDate date;
    private static Set<Long> friends;

    @BeforeAll
    public static void createFields() {
        name = "name";
        login = "login";
        email = "test@yandex.ru";
        date = LocalDate.of(1999, 10, 5);
    }

    @Test
    void testCreateUserWhenEmailNotCorrect() {
        User user = new User(name, login, "testyandex.ru", date, friends);
        assertThrows(ValidationException.class, () -> uController.create(user));
    }

    @Test
    void testCreateUserWhenEmailIsEmpty() {
        User user = new User(name, login, "", date, friends);
        assertThrows(ValidationException.class, () -> uController.create(user));
    }

    @Test
    void ShouldValidateLogin() {
        String login2 = "";
        String login3 = "lo gin";
        User user1 = new User(name, login, email, date, friends);
        User user2 = new User(name, login2, email, date, friends);
        User user3 = new User(name, login3, email, date, friends);
        try {
            uController.create(user1);
        } catch (ValidationException e) {
            System.out.println(e.getMessage());
        }
        assertEquals(user1, uController.getUsers().get(0));

    }

    @Test
    void testCreateUsersWhenNameIsNotEmpty() {
        userStorage.getUsers().forEach(user -> userStorage.delete(user));
        User user = new User(name, login, email, date, friends);
        assertDoesNotThrow(() -> uController.create(user));
        assertEquals(user, uController.getUsers().get(0));

    }

    @Test
    void ShouldValidateBirthday() {
        LocalDate date2 = LocalDate.of(2023, 10, 5);
        User user1 = new User(name, login, email, date, friends);
        User user2 = new User(name, login, email, date2, friends);
        try {
            uController.create(user1);
        } catch (ValidationException e) {
            System.out.println(e.getMessage());
        }
        assertThrows(ValidationException.class, () -> uController.create(user2));
    }

}
