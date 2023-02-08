package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@Builder(toBuilder = true)
public class User {
    private final int id;
    @Email
    private final String email;
    @NotBlank
    @NotNull
    private String login;
    private String name;
    @NotNull
    private final LocalDate birthday;
}