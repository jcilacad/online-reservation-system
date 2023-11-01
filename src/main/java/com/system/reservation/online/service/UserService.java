package com.system.reservation.online.service;

import com.system.reservation.online.dto.UserDto;
import com.system.reservation.online.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;

import java.awt.print.Pageable;
import java.util.List;

public interface UserService {

    void saveUser(UserDto userDto);

    User findUserByEmail(String email);

    List<UserDto> findAllDto();

    boolean isUserExists(UserDto user);

    boolean isUserAuthenticated(Authentication authentication);

    List<User> findAll();

    Page<User> findAllPaginated(Integer currentPage, Integer pageSize);
}
