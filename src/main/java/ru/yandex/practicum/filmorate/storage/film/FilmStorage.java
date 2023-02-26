package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {

    Film create(Film film);

    Film update(Film film);

    void delete(Film film);

    List<Film> getFilms();

    Film find(int id);

    boolean isValid(Film film);
}