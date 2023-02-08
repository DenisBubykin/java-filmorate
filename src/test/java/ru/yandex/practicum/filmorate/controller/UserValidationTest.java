package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserValidationTest {

    private final UserController userController = new UserController();

    @Test
    public void addUser_LoginHaveSpacesValidationTest() {
        User user = User.builder()
                .id(1)
                .login("dfdfdf fgr4rg")
                .email("gdsadad@efef.com")
                .name("John")
                .birthday(LocalDate.of(1995, 1, 2))
                .build();

        assertThrows(ValidationException.class, () -> userController.create(user));
    }

    @Test
    public void addUser_LoginHaveNotSpacesValidationTest() {
        User user = User.builder()
                .id(1)
                .login("dfdfdf_fgr4rg")
                .email("gdsadad@efef.com")
                .name("John")
                .birthday(LocalDate.of(1995, 1, 2))
                .build();

        assertDoesNotThrow(() -> userController.create(user));
    }


}
