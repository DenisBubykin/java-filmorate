package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.film.FilmService;

import javax.validation.Valid;
import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/films")
public class FilmController {

    private final FilmService filmService;

    public List<Film> getFilms() {
        return filmService.getFilms();
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) throws ValidationException {
        return filmService.create(film);
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film updateFilm) {
        return filmService.update(updateFilm);
    }

    @PutMapping(value = "/films/{id}/like/{userId}")
    public void addLike(@PathVariable Integer id, @PathVariable Long userId) {
        filmService.addLike(id, userId);
    }
    @DeleteMapping(value = "/films/{id}/like/{userId}")
    public void deleteLike(@PathVariable Integer id, @PathVariable Long userId) {
        filmService.deleteLike(id, userId);
    }
    @GetMapping(value = "/films/popular")
    public void getPopularFilms(@RequestParam(required = false) Integer count) {
        filmService.getPopularFilms(count);
    }

}