package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.film.FilmService;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    private final FilmService filmService;
    public final static LocalDate VALIDATE_RELEASE_DATE = LocalDate.of(1895, 12, 28);
    private int nextId = 1;
    private Map<Integer, Film> films = new HashMap<>();

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    private int getNextId() {
        return nextId++;
    }

    @PostMapping
    Film create(@Valid @RequestBody Film film) {
        return filmService.getFilmStorage().create(film);
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film updateFilm) {
        return filmService.getFilmStorage().update(updateFilm);
    }

    @GetMapping
    public List<Film> getFilms() {
        return filmService.getFilmStorage().getFilms();
    }

    public boolean isValid(Film film) {
        boolean result = false;
        if (!film.getName().isEmpty() && (film.getDescription().length() <= 200) ) {
            if (film.getReleaseDate().isAfter(VALIDATE_RELEASE_DATE) || film.getReleaseDate().isEqual(VALIDATE_RELEASE_DATE)) {
                if (film.getDuration() > 0) {
                    result = true;
                }
            }
        }
        return result;
    }

}