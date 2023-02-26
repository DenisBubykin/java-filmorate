package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@AllArgsConstructor
@Builder(toBuilder = true)
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class User {
    private int id;
    private String name;
    @NotNull
    private String login;
    @Email
    private String email;
    private LocalDate birthday;

    private Set<Long> friends;


}
