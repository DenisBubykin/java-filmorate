package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class Genre {
    private long id;
    @NotNull
    private String name;
}
