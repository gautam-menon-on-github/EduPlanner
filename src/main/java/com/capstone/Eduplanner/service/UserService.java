package com.capstone.Eduplanner.service;

import com.capstone.Eduplanner.model.User;
import com.capstone.Eduplanner.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // register new users.
    public User register(User user) {
        return userRepository.save(user);
    }

    // login existing users.
    public Optional<User> login(String email, String password) {
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isPresent() && optionalUser.get().getPassword().equals(password)) {
            return optionalUser;
        }

        return Optional.empty();
    }

    // find a user by email.
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // find a user by id.
    public Optional<User> findById(int id) {
        return userRepository.findById(id);
    }
}
