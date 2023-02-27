package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;


@AllArgsConstructor
@Builder(toBuilder = true)
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class User {

    private Integer id;

    public User(String name, String login, String email, LocalDate birthday, Set<Long> friends) {
        this.name = name;
        this.login = login;
        this.email = email;
        this.birthday = birthday;
        this.friends = friends;
    }

    private String name;
    @NotNull
    private String login;
    @Email
    private String email;
    private LocalDate birthday;
    @JsonIgnore
    private Set<Long> friends;


}
