package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString


public class Film {

    @NotNull
    private Integer id;
    @NonNull
    private String name;
    @Size(min = 1, max = 200)
    private String description;
    @NonNull
    private LocalDate releaseDate;
    @Positive
    @Min(0)
    private Integer duration;

    }
