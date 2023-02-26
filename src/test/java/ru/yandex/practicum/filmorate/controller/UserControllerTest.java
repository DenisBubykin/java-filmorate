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
    public static void createFields(){
        name = "name";
        login = "login";
        email = "test@yandex.ru";
        date = LocalDate.of(1999, 10, 5);
    }

    @Test
    void ShouldValidateUserEmail(){
        String email2 = " ";
        String email3 = "testyandex.ru";
        User user1 = new User(1,name, login, email, date, friends);
        User user2 = new User(2, name, login, email2, date, friends);
        User user3 = new User(3, name, login, email3, date, friends);
        try {
            uController.create(user1);
        } catch (ValidationException e) {
            System.out.println(e.getMessage());
        }
        assertThrows(ValidationException.class, () -> uController.update(user2));
        assertThrows(ValidationException.class, () -> uController.update(user3));
    }

    @Test
    void ShouldValidateLogin() {
        String login2 = "";
        String login3 = "lo gin";
        User user1 = new User(1, name, login, email, date, friends);
        User user2 = new User(2, name, login2, email, date, friends);
        User user3 = new User(3, name, login3, email, date, friends);
        try {
            uController.create(user1);
        } catch (ValidationException e) {
            System.out.println(e.getMessage());
        }
        assertEquals(user1, uController.getUsers().get(0));
        assertThrows(ValidationException.class, () -> uController.update(user2));
        assertThrows(ValidationException.class, () -> uController.update(user3));
    }

    @Test
    void testCreateUsermWhenNameIsNotEmpty(){
        userStorage.getUsers().forEach(user -> userStorage.delete(user));
        User user = new User(name, login, email, date, friends);
        assertDoesNotThrow(() -> uController.create(user));
        assertEquals(user, uController.getUsers().get(0));

    }

    @Test
    void ShouldValidateBirthday(){
        LocalDate date2 = LocalDate.of(2023, 10, 5);
        User user1 = new User(1, name, login, email, date, friends);
        User user2 = new User(2, name,login, email, date2, friends);
        try {
            uController.create(user1);
        } catch (ValidationException e) {
            System.out.println(e.getMessage());
        }
        assertThrows(ValidationException.class, () -> uController.update(user2));
    }
}
