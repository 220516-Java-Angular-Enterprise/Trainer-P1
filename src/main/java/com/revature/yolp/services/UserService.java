package com.revature.yolp.services;

import com.revature.yolp.daos.UserDAO;
import com.revature.yolp.dtos.requests.LoginRequest;
import com.revature.yolp.dtos.requests.NewUserRequest;
import com.revature.yolp.dtos.responses.Principal;
import com.revature.yolp.models.User;
import com.revature.yolp.util.annotations.Inject;
import com.revature.yolp.util.custom_exceptions.AuthenticationException;
import com.revature.yolp.util.custom_exceptions.InvalidRequestException;
import com.revature.yolp.util.custom_exceptions.InvalidUserException;
import com.revature.yolp.util.custom_exceptions.ResourceConflictException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/* Purpose: validation ie. checks username, password, and retrieve data from our daos. */
public class UserService {

    @Inject
    private final UserDAO userDAO;

    @Inject
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User login(LoginRequest request) {
        User user = new User();
        if (!isValidUsername(request.getUsername()) || !isValidPassword(request.getPassword())) throw new InvalidRequestException("Invalid username or password");
        user = userDAO.getUserByUsernameAndPassword(request.getUsername(), request.getPassword());
        if (user == null) throw new AuthenticationException("Invalid credentials provided!");
        return user;
    }

    public User register(NewUserRequest request) {
        User user = request.extractUser();

        if (isNotDuplicateUsername(user.getUsername())) {
            if (isValidUsername(user.getUsername())) {
                if (isValidPassword(user.getPassword())) {
                    user.setId(UUID.randomUUID().toString());
                    userDAO.save(user);
                } else throw new InvalidRequestException("Invalid password. Minimum eight characters, at least one letter, one number and one special character.");
            } else throw new InvalidRequestException("Invalid username. Username needs to be 8-20 characters long.");
        } else throw new ResourceConflictException("Username is already taken :(");

        return user;
    }

    public List<User> getAllUsers() {
        return userDAO.getAll();
    }

    public List<User> getUserByUsername(String name) {
        return userDAO.getUsersByUsername(name);
    }

    private boolean isValidUsername(String username) {
        return username.matches("^(?=[a-zA-Z0-9._]{8,20}$)(?!.*[_.]{2})[^_.].*[^_.]$");
    }

    private boolean isNotDuplicateUsername(String username) {
        return !userDAO.getAllUsernames().contains(username);
    }

    private boolean isValidPassword(String password) {
        return password.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$");
    }
}
