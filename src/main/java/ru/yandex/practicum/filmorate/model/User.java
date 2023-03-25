package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
@RequiredArgsConstructor
public class User {
    private Long id;
    private String name;
    @NotNull
    private String login;
    @Email
    private String email;
    private LocalDate birthday;
    private Set<Long> friends;
}
