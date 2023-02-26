package ru.yandex.practicum.filmorate.service.user;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
@Service
public class UserService {

    @Getter
    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public List<User> getFriends(Set<Long> ids) {
        return userStorage.getUsersByIds(ids);
    }

    public void addFriends(Long userId, Long friendId) {
        addFriend(userId, friendId);
        addFriend(friendId, userId);
    }

    private void addFriend(Long userId, Long friendId) {
        User user = userStorage.find(userId);
        userStorage.find(friendId);
        Set<Long> userFriends = user.getFriends();
        userFriends.add(friendId);
        user.setFriends(userFriends);
        userStorage.update(user);
    }

    private void deleteFriend(Long userId, Long friendId) {
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
}
