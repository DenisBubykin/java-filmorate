package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;

@Data
public class User {

    @NonNull
    private final int id;

    private final String name;
    @NonNull
    private String login;
    @NonNull
    private String email;
    @NonNull
    private final LocalDate birthday;



}
