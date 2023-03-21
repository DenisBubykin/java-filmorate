package ru.yandex.practicum.filmorate.storage.film;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validator.FilmValidator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Long, Film> films = new HashMap<>();
    private int idFilm = 0;

    @Override
    public Film addFilm(Film film) {
        FilmValidator.isValidFilms(film);
        int id = generateIdFilms();
        film.setId(id);
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        if (films.containsKey(film.getId())) {
            FilmValidator.isValidFilms(film);
            films.put(film.getId(), film);
        } else {
            throw new NotFoundException("Такого фильма нет в базе.");
        }
        return film;
    }

    @Override
    public void clearFilms() {
        if (!films.isEmpty()) {
            films.clear();
        }
    }

    @Override
    public void deleteFilmById(Long idStr) {
        if (!films.containsKey(idStr)) {
            throw new NotFoundException(String.format("Фильм № %d не найден", idStr));
        }
        films.remove(idStr);
    }

    @Override
    public List<Film> getFilms() {
        return new ArrayList<>(films.values());
    }

    @Override
    public Film findFilmById(Long idStr) {
        if (!films.containsKey(idStr)) {
            throw new NotFoundException(String.format("Фильм № %d не найден", idStr));
        }
        return getFilms().stream().filter(f -> f.getId() == idStr)
                .findFirst()
                .orElseThrow(() -> new NotFoundException(String.format("Film %d not found", idStr)));
    }

    private int generateIdFilms() {
        return ++idFilm;
    }
}
