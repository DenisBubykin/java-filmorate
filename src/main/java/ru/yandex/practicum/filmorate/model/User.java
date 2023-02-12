package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.time.LocalDate;



@AllArgsConstructor
@Builder(toBuilder = true)
@Getter
@Setter
@EqualsAndHashCode
@ToString

public class User {
    @NotBlank
    private long id;
    @NotBlank
    private String name;
    @NotBlank
    private String login;
    @Email
    private String email;
    @Past
    private LocalDate birthday;

}