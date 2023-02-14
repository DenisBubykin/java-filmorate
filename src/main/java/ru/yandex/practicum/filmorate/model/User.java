package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;



@AllArgsConstructor
@Builder(toBuilder = true)
@Getter
@Setter
@EqualsAndHashCode
@ToString

public class User {


    private long id;
    @NotNull
    private String name;
    @NotNull
    private String login;
    @Email
    private String email;
    @NotNull
    private LocalDate birthday;

}