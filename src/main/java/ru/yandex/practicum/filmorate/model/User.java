package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;

@Data
public class User {

    private final int id;
    @NonNull
    private String email;
    @NonNull
    private String login;
    private final String name;
    @NonNull
    private final LocalDate birthday;



}
