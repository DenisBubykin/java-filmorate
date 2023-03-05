package ru.yandex.practicum.filmorate.validator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;


import java.time.LocalDate;
import java.util.List;

@Slf4j
public class FilmValidator {

    public static void isValidFilms(@RequestBody Film film) throws ValidationException {
        if (film.getName().isBlank()) {
            log.warn("Ошибка в названии: {}", film);
            throw new ValidationException("Фильм не соответствует условиям: " +
                    "название не должно быть пустым");
        }
        if (film.getDescription().length() > 200) {
            log.warn("Ошибка в описании: {}", film);
            throw new ValidationException("Фильм не соответствует условиям: " +
                    "длина описания не может быть больше 200 символов");
        }
        if (film.getReleaseDate().isBefore(LocalDate.parse("1895-12-27"))) {
            log.warn("Ошибка в дате релиза: {}", film);
            throw new ValidationException("Фильм не соответствует условиям: " +
                    "дата релиза не может быть раньше 28 декабря 1895 года");
        }
        if (film.getDuration() < 0) {
            log.warn("Ошибка в продолжительности: {}", film);
            throw new ValidationException("Фильм не соответствует условиям: " +
                    "продолжительность фильма не может быть отрицательной");
        }
    }

    public static void isValidIdFilms(Long id) throws ValidationException {
        if (id < 0) {
            throw new NotFoundException(String.format("Id фильма {} отрицательный", id));
        }
    }

    public static void isFilmByFilms(List<Film> users, Film film) {
        if (!users.contains(film)) {
            throw new NotFoundException(String.format("Фильм № %d не найден", film.getId()));
        }
    }
}
