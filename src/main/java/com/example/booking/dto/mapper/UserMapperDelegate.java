package com.example.booking.dto.mapper;

import com.example.booking.dto.user.UserResponse;
import com.example.booking.entity.User;

public abstract class UserMapperDelegate implements UserMapper{

    @Override
    public UserResponse userToResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.toRole())
                .build();
    }
}
