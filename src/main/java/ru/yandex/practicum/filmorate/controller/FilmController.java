package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.film.FilmService;

import java.util.List;
import java.util.Objects;


@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    public List<Film> getFilms() {
        log.debug("Получили список всех фильмов.");
        return filmService.getFilms();
    }

    @PostMapping()
    public Film addFilm(@RequestBody Film film) {
        log.debug("Добавили: {}", film);
        return filmService.addFilm(film);
    }

    @PutMapping()
    public Film updateFilm(@RequestBody Film film) {
        log.debug("Обновили: {}", film);
        return filmService.updateFilm(film);
    }

    @DeleteMapping
    public void clearFilms() {
        log.debug("Очистили список фильмов.");
        filmService.clearFilms();
    }

    @DeleteMapping("/{id}")
    public void deleteFilmById(@PathVariable String id) {
        log.debug("Удалили фильм по id {}.", id);
        filmService.deleteFilmById(id);
    }

    @GetMapping("/{id}")
    public Film findFilmById(@PathVariable String id) {
        return filmService.findFilmById(id);
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLikeFilm(@PathVariable String id, @PathVariable String userId) {
        log.debug("Пользователь с id {} ставит лайк фильму с id {}", userId, id);
        filmService.addLikeFilms(id, userId);
    }
    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLikeFilm(@PathVariable String id, @PathVariable String userId) {
        log.debug("Пользователь с id {} удаляет лайк к фильму с id {}", userId, id);
        filmService.deleteLikeFilm(id, userId);
    }
    @GetMapping("/popular")
    public List<Film> getPopularFilms(@RequestParam(required = false) String count) {
        return filmService.getPopularFilms(Objects.requireNonNullElse(count, "10"));
    }
}