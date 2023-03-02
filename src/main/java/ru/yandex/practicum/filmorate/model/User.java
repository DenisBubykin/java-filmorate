package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;
import java.util.TreeSet;


@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
public class User {

    private Integer id;

    private String name;
    @NotNull
    private String login;
    @Email
    private String email;
    private LocalDate birthday;

    private TreeSet<Integer> friends;


}
