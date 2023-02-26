package ru.yandex.practicum.filmorate.controller;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class FilmControllerTest {
    @Autowired
    private FilmController fController;
    @Autowired
    private FilmStorage filmStorage;
    private static String name;
    private static String description;
    private static LocalDate date;
    private static Integer duration;
    private static Set<Long> likes;


    @BeforeAll
    public static void createFields() {
        name = "name";
        description = "description";
        date = LocalDate.of(1999, 10, 5);
        duration = 100;
    }

    @Test
    void testCreateFilmWhenNameIsNotEmpty(){
        filmStorage.getFilms().forEach(film -> filmStorage.delete(film));
        Film film = new Film(name, description, date, duration, likes);
        assertDoesNotThrow(() -> fController.create(film));
        assertEquals(film, fController.getFilms().get(0));

    }

    @Test
    void testCreateFilmWhenNameIsEmpty(){
        Film film = new Film("", description, date, duration, likes);
        assertThrows(ValidationException.class, () -> fController.create(film));
    }

    @Test
    void testIsValidWhenDescriptionMoreThen() {
        String line = "qwertyuiopasdftfftffufgu";
        Film film = new Film(name, line.repeat(10), date, duration, likes);
        assertThrows(ValidationException.class, () -> fController.create(film));
    }

    @Test
    void testIsValidWhenRealiseDate() {
        LocalDate date = LocalDate.of(1700, 10, 5);
        Film film = new Film(name, description, date, duration, likes);
        assertThrows(ValidationException.class, () -> fController.create(film));
    }

    @Test
    void testIsValidWhenDuration(){
        Film film = new Film(2, name, description, date, -1, likes);
        assertThrows(ValidationException.class, () -> fController.create(film));

    }

    @Test
    void ShouldValidateId(){
        Film film1 = new Film(1, name, description, date, 1, likes);
        Film film2= new Film(2, name, description, date, 2, likes);
        Film film3= new Film(3, name, description, date, 3, likes);
        try {
            fController.create(film1);
            fController.create(film2);
            fController.create(film3);
        } catch (ValidationException e) {
            System.out.println(e.getMessage());
        }
        Film updateFilm1 = new Film(1, name, description, date, 100, likes);
        Film updateFilm2 = new Film(0, name, description, date, 100, likes);
        Film updateFilm3 = new Film(-1, name, description, date, 100, likes);
        try {
            fController.update(updateFilm1);
        } catch (ValidationException e) {
            System.out.println(e.getMessage());
        }
        assertEquals(updateFilm1, fController.getFilms().get(0));
        assertThrows(ValidationException.class, () -> fController.update(updateFilm2));
        assertThrows(ValidationException.class, () -> fController.update(updateFilm3));
    }
}