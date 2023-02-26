package ru.yandex.practicum.filmorate.service.film;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilmService {
    private final FilmStorage filmStorage;
    private static final int MAX_QUANTITY_POPULAR_FILMS = 10;
    private static final String NO_SUCH_LIKE = "No likes";

    public void addLike(Integer filmId, Long userId) {
        Film film = filmStorage.find(filmId);
        Set<Long> likes = film.getLikes();
        likes.add(userId);
        film.setLikes(likes);
        filmStorage.update(film);
    }

    public void deleteLike(Integer filmId, Long userId) {
        Film film = filmStorage.find(filmId);
        Set<Long> likes = film.getLikes();
        if (likes.contains(userId)) {
            likes.remove(userId);
            film.setLikes(likes);
            filmStorage.update(film);
        } else {
            throw new NotFoundException(NO_SUCH_LIKE);
        }
    }
    public Set<Film> getPopularFilms(Integer filmQuantity) {
        Comparator<Film> filmLikeComparator = (film1, film2) -> {
            if (film1.getLikes().size() == film2.getLikes().size()) {
                return (film1.getId() - film2.getId());
            } else {
                return film1.getLikes().size() - film2.getLikes().size();
            }
        };
        Set<Film> popularFilms = new TreeSet<>(filmLikeComparator.reversed());
        List<Film> films = filmStorage.getFilms();
        popularFilms.addAll(films);
        if (Objects.isNull(filmQuantity)) {
            filmQuantity = MAX_QUANTITY_POPULAR_FILMS;
        }
        return popularFilms.stream().limit(filmQuantity).collect(Collectors.toSet());
    }

    public List<Film> getFilms() {
        return filmStorage.getFilms();
    }

    public Film create(Film film) {
        return filmStorage.create(film);
    }

    public Film update(Film updateFilm) {
        return filmStorage.update(updateFilm);
    }
}