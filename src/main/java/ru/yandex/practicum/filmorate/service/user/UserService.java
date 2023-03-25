package ru.yandex.practicum.filmorate.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import ru.yandex.practicum.filmorate.validator.UserValidator;

import java.util.*;

@Service
public class UserService {

    private final UserStorage userStorage;

    @Autowired
    public UserService(@Qualifier("userDbStorage")UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public List<User> getUsers() {
        return userStorage.getUsers();
    }

    public User createUser(User user) {
        return userStorage.createUser(user);
    }

    public User updateUser(User user) {
        return userStorage.updateUser(user);
    }

    public void clearUsers() {
        userStorage.clearUsers();
    }

    public void deleteUserById(Long id) {
        userStorage.deleteUserById(id);
    }

    public User findUserById(Long id) {
        return userStorage.findUserById(id);
    }

    public void addFriendsForUsers(Long userIdStr, Long friendIdStr) {
        addFriends(userIdStr, friendIdStr, userStorage.findUserById(userIdStr));
        addFriends(friendIdStr, userIdStr, userStorage.findUserById(friendIdStr));
    }

    private void addFriends(Long idUser, Long idFriend, User userById) {
        UserValidator.isValidIdUsers(idUser);
        UserValidator.isValidIdUsers(idFriend);
        UserValidator.isUserByUsers(userStorage.getUsers(), userStorage.findUserById(idUser));
        UserValidator.isUserByUsers(userStorage.getUsers(), userStorage.findUserById(idFriend));

        if (isFriendsByUser(idUser)) {
            Set<Long> friends = userById.getFriends();
            friends.add(idFriend);
            userById.setFriends(friends);
        } else {
            TreeSet<Long> friends = new TreeSet<>();
            friends.add(idFriend);
            userById.setFriends(friends);
        }
    }

    private Boolean isFriendsByUser(Long id) {
        return userStorage.findUserById(id).getFriends() != null;
    }

    public void deleteFriendsForUsers(Long userIdStr, Long friendIdStr) {
        deleteFriend(userIdStr, friendIdStr, userStorage.findUserById(userIdStr));
        deleteFriend(friendIdStr, userIdStr, userStorage.findUserById(friendIdStr));
    }

    public void deleteFriend(Long idUser, Long idFriend, User userById) {
        UserValidator.isValidIdUsers(idUser);
        UserValidator.isValidIdUsers(idFriend);
        UserValidator.isUserByUsers(userStorage.getUsers(), userStorage.findUserById(idUser));
        UserValidator.isUserByUsers(userStorage.getUsers(), userStorage.findUserById(idFriend));

        if (isFriendsByUser(idUser)) {
            if (!userById.getFriends().contains(idFriend)) {
                throw new NotFoundException(String.format(
                        "У пользователя № %d нет в друзьях пользователя № %d", idUser, idFriend));
            }
            Set<Long> friends1 = userById.getFriends();
            friends1.remove(idFriend);
            userById.setFriends(friends1);
        } else {
            throw new NotFoundException(String.format("У пользователя № %d нет друзей", idUser));
        }
    }

    public List<User> getFriendsUser(Long idStr) {
        List<Long> idFriendsList = new ArrayList<>(userStorage.findUserById(idStr).getFriends());
        List<User> friendsList = new ArrayList<>();
        for (Long idFriends : idFriendsList) {
            friendsList.add(userStorage.findUserById(Long.valueOf(idFriends)));
        }
        return friendsList;
    }

    public List<User> getListFriends(Long id, Long otherId) {
        UserValidator.isUserByUsers(userStorage.getUsers(), userStorage.findUserById(id));
        UserValidator.isUserByUsers(userStorage.getUsers(), userStorage.findUserById(otherId));

        List<User> result = new ArrayList<>();

        if (userStorage.findUserById(otherId).getFriends() != null || userStorage.findUserById(id).getFriends() != null) {
            for (Long friendOtherId : userStorage.findUserById(otherId).getFriends()) {
                for (Long friendId : userStorage.findUserById(id).getFriends()) {
                    if (friendOtherId.equals(friendId)) {
                        result.add(userStorage.findUserById(Long.valueOf(friendId)));
                    }
                }
            }
        }

        return result;
    }
}