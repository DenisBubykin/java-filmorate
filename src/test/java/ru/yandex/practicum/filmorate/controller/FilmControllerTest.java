package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.Duration;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FilmControllerTest {
    private final FilmController filmController = new FilmController();

    @Test
    public void addFilm_BadReleaseDateValidationTest() {
        Film film = Film.builder()
                .id(1)
                .name("Blabla")
                .description("some description")
                .releaseDate(LocalDate.of(1895, 12, 27))
                .duration(Duration.ofMinutes(130))
                .build();

        assertThrows(ValidationException.class, () -> filmController.addFilm(film));
    }

    @Test
    public void addFilm_GoodReleaseDateValidationTest() {
        Film film = Film.builder()
                .id(2)
                .name("Foo")
                .description("some description")
                .releaseDate(LocalDate.of(1895, 12, 29))
                .duration(Duration.ofMinutes(130))
                .build();

        assertDoesNotThrow(() -> filmController.addFilm(film));
    }
}