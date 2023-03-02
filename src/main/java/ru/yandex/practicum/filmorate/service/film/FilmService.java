package ru.yandex.practicum.filmorate.service.film;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.validator.FilmValidator;
import ru.yandex.practicum.filmorate.validator.UserValidator;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FilmService {
    @Getter
    private final FilmStorage filmStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public List<Film> getFilms() {
        return filmStorage.getFilms();
    }

    public Film addFilm(Film film) {
        return filmStorage.addFilm(film);
    }

    public Film updateFilm(Film film) {
        return filmStorage.updateFilm(film);
    }

    public void clearFilms() {
        filmStorage.clearFilms();
    }

    public void deleteFilmById(String idStr) {
        filmStorage.deleteFilmById(idStr);
    }

    public Film findFilmById(String idStr) {
        return filmStorage.findFilmById(idStr);
    }

    public void addLikeFilms(String idFilm, String idUser) {
        int userId = Integer.parseInt(idUser);
        int filmId = Integer.parseInt(idFilm);
        FilmValidator.isValidIdFilms(filmId);
        UserValidator.isValidIdUsers(userId);
        FilmValidator.isFilmByFilms(filmStorage.getFilms(), findFilmById(idFilm));

        if (isLikesByFilm(idFilm)) {
            Set<Integer> likes = findFilmById(idFilm).getUsersLike();
            likes.add(userId);
            findFilmById(idFilm).setUsersLike(likes);
        } else {
            TreeSet<Integer> likes = new TreeSet<>();
            likes.add(userId);
            findFilmById(idFilm).setUsersLike(likes);
        }
    }

    private Boolean isLikesByFilm(String id) {
        return filmStorage.findFilmById(id).getUsersLike() != null;
    }

    public void deleteLikeFilm(String idFilm, String idUser) {
        int userId = Integer.parseInt(idUser);
        int filmId = Integer.parseInt(idFilm);
        FilmValidator.isValidIdFilms(filmId);
        UserValidator.isValidIdUsers(userId);
        FilmValidator.isFilmByFilms(filmStorage.getFilms(), findFilmById(idFilm));

        if (isLikesByFilm(idFilm)) {
            Set<Integer> likes = findFilmById(idFilm).getUsersLike();
            likes.remove(userId);
            findFilmById(idFilm).setUsersLike(likes);
        } else {
            throw new NotFoundException(String.format("У фильма № %d нет лайков", filmId));
        }
    }

    public List<Film> getPopularFilms(String countStr) {
        int count = Integer.parseInt(countStr);
        if (filmStorage.getFilms() == null) {
            throw new NotFoundException("Список фильмов пуст.");
        }
        return filmStorage.getFilms().stream()
                .sorted(this::compareFilmsReverse)
                .limit(count)
                .collect(Collectors.toList());
    }

    private int compareFilmsReverse(Film f1, Film f2) {
        int comp1 = 0;
        int comp2 = 0;
        if (f1.getUsersLike() != null) {
            comp1 = f1.getUsersLike().size();
        }
        if (f2.getUsersLike() != null) {
            comp2 = f2.getUsersLike().size();
        }
        return comp2 - comp1;
    }

}
