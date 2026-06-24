package com.bakerypos.bakery_pos.service;

import java.util.Optional;
import org.springframework.stereotype.Service;
import com.bakerypos.bakery_pos.model.User;
import com.bakerypos.bakery_pos.repository.UserRepository;

@Service
public class AuthService{
    private final UserRepository userRepository;
    public AuthService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User login(String noTelp, String password){
        Optional<User> user = userRepository.findByNoTelpAndPassword(noTelp, password);
        return user.orElse(null);
    }
}