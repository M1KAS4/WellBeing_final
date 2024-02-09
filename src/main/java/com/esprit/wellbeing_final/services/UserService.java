package com.esprit.wellbeing_final.services;

import com.esprit.wellbeing_final.entities.User;

import java.util.List;

public interface UserService {
    User login (String email, String password);

    void createUser(User user);
    public List<User> getAllUsers();
    void updateUser(User user);
    void deleteUser(long userId);
    List<User> searchUsers(String searchTerm);

}
