package com.example.booking.service;


import com.example.booking.entity.Role;
import com.example.booking.entity.User;
import com.example.booking.repo.UserRepository;
import com.example.booking.statistics.model.UserStatistic;
import com.example.booking.util.AppHelperUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final KafkaTemplate<String, UserStatistic> userKafkaTemplate;

    @Value("${app.kafka.kafkaUserTopic}")
    private String topicUserName;

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        MessageFormatter.format("User with id={} not found", id).getMessage()));
    }

    @Override
    public User save(User user, Role role) {
        if (userRepository.existsUserByUsernameAndEmail(user.getUsername(), user.getEmail())) {
            throw new RuntimeException(
                    MessageFormatter.format("User with username={} and email={} already exist",
                            user.getUsername(), user.getEmail()).getMessage());
        }
        user.setRoles(Collections.singletonList(role));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        role.setUser(user);
        user = userRepository.save(user);
        log.info("UserServiceImpl -> save. Created user with id={}", user.getId());

        UserStatistic userStatistic = new UserStatistic(user.getId());
        userKafkaTemplate.send(topicUserName, userStatistic);
        log.info("UserServiceImpl -> save. User send to kafka");

        return user;
    }

    @Override
    public User update(User user) {
        User existedUser = findById(user.getId());
        AppHelperUtils.copyNonNullProperties(user, existedUser);

        return userRepository.save(existedUser);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        MessageFormatter.format(
                                "User with username {} not found", username).getMessage()));
    }

}
