package com.example.booking.service;

import com.example.booking.entity.Role;
import com.example.booking.entity.User;

public interface UserService {



    User findById(Long id);

    User save(User user, Role role);

    User update(User user);

    void deleteById(Long id);

    User findByUsername(String username);

}
