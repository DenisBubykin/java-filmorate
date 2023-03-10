package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

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
    public final static LocalDate VALIDATE_RELEASE_DATE = LocalDate.of(1895, 12, 28);
    private int nextId = 1;
    private Map<Integer, Film> films = new HashMap<>();

    private int getNextId() {
        return nextId++;
    }

    @PostMapping
    @Valid
    Film create(@Valid @RequestBody Film film) throws ValidationException {
        if (isValid(film)) {
            int filmId = getNextId();
            film.setId(filmId);
            films.put(filmId, film);
            log.info("POST /films request received");
        } else {
            throw new ValidationException("Create film is not valid");
        }
        return film;
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film updateFilm) {
        log.info("PUT /films request received");
        int updateId = updateFilm.getId();
        if (films.containsKey(updateId)) {
            if (isValid(updateFilm)){
                films.put(updateId, updateFilm);
            } else {
                log.error("Request PUT /films contains invalid data");
                throw new ValidationException("Update film data is not valid");
            }
        } else {
            log.error("Request PUT /films contains invalid id");
            throw new ValidationException("Update film id is not valid");
        }
        log.info("PUT /films request done");
        return updateFilm;
    }

    @GetMapping
    public List<Film> getFilms() {
        List<Film> list = new ArrayList<>(films.values());
        log.info("GET /films request done");
        return list;
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