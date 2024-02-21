package com.example.booking.service;


import com.example.booking.dto.mapper.UserMapper;
import com.example.booking.dto.user.UserRq;
import com.example.booking.dto.user.UserRs;
import com.example.booking.entity.Role;
import com.example.booking.entity.RoleType;
import com.example.booking.entity.User;
import com.example.booking.repo.UserRepository;
import com.example.booking.entity.UserStatistic;
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
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final KafkaTemplate<String, UserStatistic> userKafkaTemplate;


    @Value("${app.kafka.kafkaUserTopic}")
    private String topicUserName;

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        MessageFormatter.format("User with id={} not found", id).getMessage()));
    }

    public UserRs findByIdRs(Long id) {
        return userMapper.userToResponse(findById(id));
    }

    public UserRs save(UserRq rq, RoleType type) {
        User user = userMapper.requestToUser(rq);
        Role role = Role.from(type);
        if (userRepository.existsUserByUsernameAndEmail(user.getUsername(), user.getEmail())) {
            throw new RuntimeException(
                    MessageFormatter.format("User with username={} and email={} already exist",
                            user.getUsername(), user.getEmail()).getMessage());
        }
        user.setRoles(Collections.singletonList(role));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        role.setUser(user);
        user = userRepository.save(user);
        log.info("UserService -> save. Created user with id={}", user.getId());

        UserStatistic userStatistic = new UserStatistic(user.getId());
        userKafkaTemplate.send(topicUserName, userStatistic);
        log.info("UserService -> save. User send to kafka");

        return userMapper.userToResponse(user);
    }


    public UserRs update(Long id, UserRq rq) {
        User existedUser = findById(id);
        List<Role> roles = existedUser.getRoles();
        AppHelperUtils.copyNonNullProperties(userMapper.requestToUser(rq), existedUser);
        existedUser.setRoles(roles);
        return userMapper.userToResponse(userRepository.save(existedUser));
    }


    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }


    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        MessageFormatter.format(
                                "User with username {} not found", username).getMessage()));
    }

}
