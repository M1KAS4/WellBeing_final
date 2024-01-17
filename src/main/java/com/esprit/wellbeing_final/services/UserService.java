package com.esprit.wellbeing_final.services;

import com.esprit.wellbeing_final.entities.User;

public interface UserService {
    User login (String email, String password);

}
