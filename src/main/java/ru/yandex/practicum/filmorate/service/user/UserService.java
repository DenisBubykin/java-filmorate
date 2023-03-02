package ru.yandex.practicum.filmorate.service.user;

import org.springframework.beans.factory.annotation.Autowired;
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
    public UserService(UserStorage userStorage) {
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

    public void deleteUserById(String idStr) {
        int id = Integer.parseInt(idStr);
        userStorage.deleteUserById(id);
    }

    public User findUserById(String idStr) {
        return userStorage.findUserById(idStr);
    }

    public void addFriendsForUsers(String userIdStr, String friendIdStr) {
        addFriends(userIdStr, friendIdStr, userStorage.findUserById(userIdStr));
        addFriends(friendIdStr, userIdStr, userStorage.findUserById(friendIdStr));
    }

    private void addFriends(String idUser, String idFriend, User userById) {
        int id = Integer.parseInt(idUser);
        int friendId = Integer.parseInt(idFriend);
        UserValidator.isValidIdUsers(id);
        UserValidator.isValidIdUsers(friendId);
        UserValidator.isUserByUsers(userStorage.getUsers(), userStorage.findUserById(idUser));
        UserValidator.isUserByUsers(userStorage.getUsers(), userStorage.findUserById(idFriend));

        if (isFriendsByUser(idUser)) {
            TreeSet<Integer> friends = userById.getFriends();
            friends.add(friendId);
            userById.setFriends(friends);
        } else {
            TreeSet<Integer> friends = new TreeSet<>();
            friends.add(friendId);
            userById.setFriends(friends);
        }
    }

    private Boolean isFriendsByUser(String id) {
        return userStorage.findUserById(id).getFriends() != null;
    }

    public void deleteFriendsForUsers(String userIdStr, String friendIdStr) {
        deleteFriend(userIdStr, friendIdStr, userStorage.findUserById(userIdStr));
        deleteFriend(friendIdStr, userIdStr, userStorage.findUserById(friendIdStr));
    }

    public void deleteFriend(String idUser, String idFriend, User userById) {
        int id = Integer.parseInt(idUser);
        int friendId = Integer.parseInt(idFriend);
        UserValidator.isValidIdUsers(id);
        UserValidator.isValidIdUsers(friendId);
        UserValidator.isUserByUsers(userStorage.getUsers(), userStorage.findUserById(idUser));
        UserValidator.isUserByUsers(userStorage.getUsers(), userStorage.findUserById(idFriend));

        if (isFriendsByUser(idUser)) {
            if (!userById.getFriends().contains(friendId)) {
                throw new NotFoundException(String.format(
                        "У пользователя № %d нет в друзьях пользователя № %d", id, friendId));
            }
            TreeSet<Integer> friends1 = userById.getFriends();
            friends1.remove(friendId);
            userById.setFriends(friends1);
        } else {
            throw new NotFoundException(String.format("У пользователя № %d нет друзей", id));
        }
    }

    public List<User> getFriendsUser(String idStr) {
        List<Integer> idFriendsList = new ArrayList<>(userStorage.findUserById(idStr).getFriends());
        List<User> friendsList = new ArrayList<>();
        for (Integer idFriends : idFriendsList) {
            friendsList.add(userStorage.findUserById(String.valueOf(idFriends)));
        }
        return friendsList;
    }

    public List<User> getListFriends(String id, String otherId) {
        UserValidator.isUserByUsers(userStorage.getUsers(), userStorage.findUserById(id));
        UserValidator.isUserByUsers(userStorage.getUsers(), userStorage.findUserById(otherId));

        List<User> result = new ArrayList<>();

        if (userStorage.findUserById(otherId).getFriends() != null || userStorage.findUserById(id).getFriends() != null) {
            for (Integer friendOtherId : userStorage.findUserById(otherId).getFriends()) {
                for (Integer friendId : userStorage.findUserById(id).getFriends()) {
                    if (friendOtherId.equals(friendId)) {
                        result.add(userStorage.findUserById(String.valueOf(friendId)));
                    }
                }
            }
        }

        return result;
    }
}