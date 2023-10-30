package com.system.reservation.online.service;

import com.system.reservation.online.dto.UserDto;
import com.system.reservation.online.entity.User;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface UserService {

    void saveUser(UserDto userDto);

    User findUserByEmail(String email);

    List<UserDto> findAll();

    boolean isUserExists(UserDto user);

    boolean isUserAuthenticated(Authentication authentication);
}
