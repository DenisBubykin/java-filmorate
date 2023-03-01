package ru.yandex.practicum.filmorate.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.film.FilmService;

import javax.validation.Valid;
import java.util.List;


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
    public List<Film> findAll() {
        return filmService.getFilmStorage().findAll();
    }

    @GetMapping(value = "/films/popular")
    public void getPopularFilms(@RequestParam(required = false) Integer count) {
        filmService.getPopularFilms(count);
    }

    @GetMapping("/films/{id}")
    public Film find(@PathVariable Long id) {
        return filmService.getFilmStorage().find(id);
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film)  {
        return filmService.getFilmStorage().create(film);
    }

    @PutMapping("/films")
    public Film amend(@Valid @RequestBody Film film) {
        return filmService.getFilmStorage().amend(film);
    }

    @PutMapping(value = "/films/{id}/like/{userId}")
    public void addLike(@PathVariable Long id, @PathVariable Long userId) {
        filmService.addLike(id, userId);
    }
    @DeleteMapping(value = "/films/{id}/like/{userId}")
    public void deleteLike(@PathVariable Long id, @PathVariable Long userId) {
        filmService.deleteLike(id, userId);
    }


}