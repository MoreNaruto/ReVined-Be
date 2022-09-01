package com.revined.revined.service;

import com.revined.revined.exception.UserNotFoundException;
import com.revined.revined.model.User;
import com.revined.revined.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User getUserByUsername(String username) throws UserNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(() -> new UserNotFoundException("Can't find user"));
    }
}
