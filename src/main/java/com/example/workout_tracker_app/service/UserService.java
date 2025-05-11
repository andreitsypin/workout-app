package com.example.workout_tracker_app.service;

import com.example.workout_tracker_app.model.User;
import com.example.workout_tracker_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user){
        return userRepository.save(user);
    }

    public User findById(Long id){
        return userRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id){
        userRepository.deleteById(id);
    }
}
