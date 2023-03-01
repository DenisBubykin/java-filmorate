package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class User {

    private Long id;

    private String name;
    @UserLoginConstraint
    private String login;
    @NotBlank
    @Email
    private String email;
    @Past
    private LocalDate birthday;
    @JsonIgnore
    private Set<Long> friends;


}
