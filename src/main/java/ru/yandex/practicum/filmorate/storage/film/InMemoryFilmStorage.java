package ru.yandex.practicum.filmorate.storage.film;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Integer, Film> films = new HashMap<>();
    private int nextId = 1;
    public final static LocalDate VALIDATE_RELEASE_DATE = LocalDate.of(1895, 12, 28);
    private static final Logger log = LoggerFactory.getLogger(InMemoryFilmStorage.class);
    private static final String WRONG_ID = "Not film's Id";

    private int getNextId() {
        return nextId++;
    }

    @Override
    public Film create(Film film) {
        if (isValid(film)) {
            int filmId = getNextId();
            film.setId(filmId);
            films.put(filmId, film);
            log.debug("Добавлен фильм: {}", film);
        } else {
            throw new ValidationException("Create film is not valid");
        }
        return film;
    }

    @Override
    public Film amend(Film film) {
        Film oldFilm;
        if (films.containsKey(film.getId())) {
            oldFilm = films.get(film.getId());
            films.put(film.getId(), film);
            log.debug("Фильм {} изменен на {}", oldFilm, film);
        } else {
            throw new ValidationException(WRONG_ID);
        }
        return film;
    }

    @Override
    public void delete(Film film) {
        if (films.containsKey(film.getId())) {
            films.remove(film.getId());
            log.debug("Фильм {} удалён", film);
        } else {
            throw new ValidationException(WRONG_ID);
        }
    }

    @Override
    public List<Film> findAll() {
        List<Film> filmList = new ArrayList<>(films.values());
        log.debug("Текущее количесвто фильмов: {}", filmList.size());
        return filmList;
    }

    @Override
    public Film find(Long id) {
        if (films.containsKey(id)) {
            return films.get(id);
        } else {
            throw new ValidationException(WRONG_ID);
        }
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


