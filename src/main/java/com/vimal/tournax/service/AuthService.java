package com.vimal.tournax.service;

import com.vimal.tournax.entity.User;
import java.util.List;

public interface AuthService {
    User registerUser(User user);
    List<User> getAllUsers();
}
