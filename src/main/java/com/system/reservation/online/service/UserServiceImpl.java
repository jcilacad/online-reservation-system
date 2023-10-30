package com.system.reservation.online.service;

import com.system.reservation.online.dto.UserDto;
import com.system.reservation.online.entity.Role;
import com.system.reservation.online.entity.User;
import com.system.reservation.online.repository.RoleRepository;
import com.system.reservation.online.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(UserDto userDto) {

        User user = new User();
        user.setName(userDto.getFirstName() + " " + userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        Role role;
        Optional<Role> result = roleRepository.findByName("ROLE_ADMIN");

        if (result.isPresent()) {
            role = result.get();
        } else {
            role = checkRoleExist();
        }

        user.setRoles(Arrays.asList(role));

        userRepository.save(user);
    }

    @Override
    public User findUserByEmail(String email) {

        User user = null;
        Optional<User> result = userRepository.findByEmail(email);

        if (result.isPresent()) {
            user = result.get();
        }

        return user;
    }

    @Override
    public List<UserDto> findAll() {
        List<User> userList = userRepository.findAll();

        return userList.stream()
                .map(user -> mapUserToDto(user))
                .collect(Collectors.toList());

    }

    private UserDto mapUserToDto(User user) {

        UserDto userDto = new UserDto();
        String[] name = user.getName().split(" ");

        userDto.setFirstName(name[0]);
        userDto.setLastName(name[1]);
        userDto.setEmail(user.getEmail());

        return userDto;

    }

    private Role checkRoleExist() {

        Role role = new Role();
        role.setName("ROLE_ADMIN");
        return roleRepository.save(role);
    }

    @Override
    public boolean isUserExists(UserDto user) {

        String email = user.getEmail();
        User findUser = findUserByEmail(email);

        return (findUser != null && findUser.getEmail() != null && !findUser.getEmail().isEmpty());
    }

    @Override
    public boolean isUserAuthenticated(Authentication authentication) {
        return authentication == null || authentication instanceof AnonymousAuthenticationToken;
    }
}
