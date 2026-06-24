package com.bakerypos.bakery_pos.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.bakerypos.bakery_pos.repository.UserRepository;
import com.bakerypos.bakery_pos.model.User;

@Service
public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public List<User> getAllUser(){
        return userRepository.findAll();
    }

    public User getUserById(int id){
        return userRepository.findById(id).orElse(null);
    }

    public void save(User user){
        userRepository.save(user);
    }

    public void delete(int id){
        userRepository.deleteById(id);
    }
}