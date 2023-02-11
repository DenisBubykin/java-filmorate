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

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    public final static LocalDate VALIDATE_RELEASE_DATE = LocalDate.of(1895, 12, 28);
    private int nextId = 0;
    private HashMap<Integer, Film> films = new HashMap<>();

    private int getNextId() {
        return ++nextId;
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) throws ValidationException {
        log.info("POST /films request received");
        Film addFilm;
        if (isValid(film)) {
            addFilm = new Film(getNextId(), film.getName(),
                    film.getDescription(), film.getReleaseDate(), film.getDuration());
            films.put(addFilm.getId(), addFilm);
        } else {
            log.error("Request POST /films contains invalid data");
            throw new ValidationException("New film data is not valid");
        }
        log.info("POST /films request done");
        return addFilm;
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film updateFilm) throws ValidationException {
        log.info("PUT /films request received");
        Integer updateId = updateFilm.getId();
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
    public List<Film> getFilms(){
        log.info("Get all films {}", films.size());
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