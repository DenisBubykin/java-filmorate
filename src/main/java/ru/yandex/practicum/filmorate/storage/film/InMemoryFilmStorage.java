package ru.yandex.practicum.filmorate.storage.film;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private int id = 1;
    private final Map<Integer, Film> films = new HashMap<>();

    private static final Logger log = LoggerFactory.getLogger(InMemoryFilmStorage.class);

    @Override
    public Film create(Film film) {
        film.setId(id);
        films.put(id, film);
        id++;
        log.debug("Добавлен фильм: {}", film);
        return film;

    }

    @Override
    public Film update(Film film) {
        Film oldFilm;
        if (films.containsKey(film.getId())) {
            oldFilm = films.get(film.getId());
            films.put(film.getId(), film);
            log.debug("Фильм {} изменен на {}", oldFilm, film);
        } else {
            throw new ValidationException("Фильма с таким Id нет");
        }
        return film;
    }

    @Override
    public List<Film> getFilms() {
        List<Film> filmList = new ArrayList<>(films.values());
        log.debug("Текущее количесвто фильмов: {}", filmList.size());
        return filmList;
    }

    @Override
    public Film find(Long id) {
        if (films.containsKey(id)) {
            return films.get(id);
        } else {
            throw new ValidationException("Фильма с таким Id нет");
        }
    }
}


