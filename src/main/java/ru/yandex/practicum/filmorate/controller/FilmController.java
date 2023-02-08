package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.*;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {

    Map<Long, Film> films = new HashMap<>();

    @PostMapping()
    public Film addFilm(@Valid @RequestBody Film film) {
        if(film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.warn(film.getReleaseDate().toString());
            throw new ValidationException("Release date can't be before 28 dec. 1895 года");
        }
        log.debug(film.toString());
        films.put(film.getId(), film);
        return film;
    }

    @PutMapping()
    public Film updateFilm(@Valid @RequestBody Film film) {
        if(film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.warn(film.getReleaseDate().toString());
            throw new ValidationException("Release date can't be before 28 dec. 1895 года");
        }
        log.debug(film.toString());
        films.put(film.getId(), film);
        return film;
    }

    @GetMapping()
    public List<Film> getAllFilms() {
        return films.values().stream().toList();
    }
}