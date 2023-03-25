package ru.yandex.practicum.filmorate.storage.film;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Rating;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FilmDbStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Film addFilm(Film film) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("film")
                .usingGeneratedKeyColumns("id");

        Map<String, Object> values = new HashMap<>();
        values.put("name", film.getName());
        values.put("description", film.getDescription());
        values.put("release_date", film.getReleaseDate());
        values.put("duration", film.getDuration());
        values.put("rating_id", film.getRating().getId());

        film.setId(simpleJdbcInsert.executeAndReturnKey(values).longValue());
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        String sql = "UPDATE film SET name = ?, description = ?, release_date = ?, duration = ?, rating_id = ? " +
                        "WHERE id = ?";
        jdbcTemplate.update(sql, film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(),
                film.getRating().getId(), film.getId());
        return film;
    }

    @Override
    public void clearFilms() {
        String sql = "TRUNCATE TABLE film";
        jdbcTemplate.update(sql);
    }

    @Override
    public void deleteFilmById(Long id) {
        String sql = "DELETE FROM film WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<Film> getFilms() {
        String sql = "SELECT f.id, f.name, f.description, f.release_date, f.duration, r.id, r.name " +
                        "FROM film f JOIN rating r ON f.rating_id = r.id ORDER BY f.id";
        return jdbcTemplate.query(sql, this::mapToFilm);
    }

    @Override
    public Film findFilmById(Long id) {
        String sql = "SELECT f.id, f.name, f.description, f.release_date, f.duration, r.id, r.name " +
                        "FROM film f JOIN rating r ON f.rating_id = r.id WHERE f.id = ?";
        List<Film> result = jdbcTemplate.query(sql, this::mapToFilm, id);
        if (result.isEmpty()) {
            return null;
        }
        return result.get(0);
    }

    private Film mapToFilm(ResultSet resultSet, int rowNum) throws SQLException {
        Film film = new Film();
        film.setId(resultSet.getLong("id"));
        film.setName(resultSet.getString("name"));
        film.setDescription(resultSet.getString("description"));
        film.setReleaseDate(resultSet.getDate("release_date").toLocalDate());
        film.setDuration(resultSet.getInt("duration"));
        film.setRating(new Rating(resultSet.getLong("id"), resultSet.getString("name")));
        return film;
    }
}
