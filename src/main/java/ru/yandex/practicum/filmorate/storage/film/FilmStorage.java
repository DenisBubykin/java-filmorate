package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {

    Film addFilm(Film film);

    Film updateFilm(Film film);
    void clearFilms();

    void deleteFilmById(Long id);

    List<Film> getFilms();

    Film findFilmById(Long id);

}
