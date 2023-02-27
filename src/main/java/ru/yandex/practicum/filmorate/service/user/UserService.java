package ru.yandex.practicum.filmorate.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
@Service
public class UserService {


    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public void addFriend(Long userId, Long friendId) {
        User user = userStorage.find(userId);
        userStorage.find(friendId);
        Set<Long> userFriends = user.getFriends();
        userFriends.add(friendId);
        user.setFriends(userFriends);
        userStorage.update(user);
    }

    public void deleteFriend(Long userId, Long friendId) {
        User user = userStorage.find(userId);
        Set<Long> userFriends = user.getFriends();
        userFriends.remove(friendId);
        user.setFriends(userFriends);
        userStorage.update(user);
    }

    private List<User> addCommonFriends(Set<Long> userFriendsIds, Set<Long> otherIdFriendsIds) {
        List<User> commonFriends = new ArrayList<>();
        for (Long id : userFriendsIds) {
            if (otherIdFriendsIds.contains(id)) {
                commonFriends.add(userStorage.find(id));
            }
        }
        return commonFriends;
    }

    public List<User> getUsers() {
        return userStorage.getUsers();
    }

    public User create(User user) {
        return userStorage.create(user);
    }

    public User update(User updateUser) {
        return userStorage.update(updateUser);
    }


    public List<User> getFriends(Long userId, Long otherId) {
        Set<Long> userFriendsIds = userStorage.find(userId).getFriends();
        Set<Long> otherIdFriendsIds = userStorage.find(otherId).getFriends();
        if (Objects.nonNull(userFriendsIds) && Objects.nonNull(otherIdFriendsIds)) {
            return addCommonFriends(userFriendsIds, otherIdFriendsIds);
        }
        return new ArrayList<>();
    }
}

