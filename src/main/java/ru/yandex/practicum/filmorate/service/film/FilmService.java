package ru.yandex.practicum.filmorate.service.film;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    public FilmService(@Qualifier("filmDbStorage") FilmStorage filmStorage) {
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

    public void deleteFilmById(Long idStr) {
        filmStorage.deleteFilmById(idStr);
    }

    public Film findFilmById(Long idStr) {
        return filmStorage.findFilmById(idStr);
    }

    public void addLikeFilms(Long idFilm, Long idUser) {
        FilmValidator.isValidIdFilms(idFilm);
        UserValidator.isValidIdUsers(idUser);
        FilmValidator.isFilmByFilms(filmStorage.getFilms(), findFilmById(idFilm));

        if (isLikesByFilm(idFilm)) {
            Set<Long> likes = findFilmById(idFilm).getLikes();
            likes.add(idUser);
            findFilmById(idFilm).setLikes(likes);
        } else {
            TreeSet<Long> likes = new TreeSet<>();
            likes.add(idUser);
            findFilmById(idFilm).setLikes(likes);
        }
    }

    private Boolean isLikesByFilm(Long id) {
        return filmStorage.findFilmById(id).getLikes() != null;
    }

    public void deleteLikeFilm(Long idFilm, Long idUser) {
        FilmValidator.isValidIdFilms(idFilm);
        UserValidator.isValidIdUsers(idUser);
        FilmValidator.isFilmByFilms(filmStorage.getFilms(), findFilmById(idFilm));

        if (isLikesByFilm(idFilm)) {
            Set<Long> likes = findFilmById(idFilm).getLikes();
            likes.remove(idUser);
            findFilmById(idFilm).setLikes(likes);
        } else {
            throw new NotFoundException(String.format("У фильма № %d нет лайков", idFilm));
        }
    }

    public List<Film> getPopularFilms(Integer countStr) {
        if (filmStorage.getFilms() == null) {
            throw new NotFoundException("Список фильмов пуст.");
        }
        return filmStorage.getFilms().stream()
                .sorted(this::compareFilmsReverse)
                .limit(countStr)
                .collect(Collectors.toList());
    }

    private int compareFilmsReverse(Film f1, Film f2) {
        int comp1 = 0;
        int comp2 = 0;
        if (f1.getLikes() != null) {
            comp1 = f1.getLikes().size();
        }
        if (f2.getLikes() != null) {
            comp2 = f2.getLikes().size();
        }
        return comp2 - comp1;
    }

}
