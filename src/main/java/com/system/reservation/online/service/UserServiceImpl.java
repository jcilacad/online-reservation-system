package com.system.reservation.online.service;

import com.system.reservation.online.dto.UserDto;
import com.system.reservation.online.entity.Role;
import com.system.reservation.online.entity.User;
import com.system.reservation.online.repository.RoleRepository;
import com.system.reservation.online.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
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
        user.setName(userDto.getLastName().toUpperCase() + ", " + userDto.getFirstName().toUpperCase() + " " + userDto.getMiddleName().toUpperCase());
        user.setStudentNumber(userDto.getStudentNumber());
        user.setEmail(userDto.getEmail());
        user.setContactNumber(userDto.getContactNumber());
        user.setPassword(passwordEncoder.encode(userDto.getStudentNumber()));

        Role role;
        Optional<Role> result = roleRepository.findByName("ROLE_STUDENT");

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
    public List<UserDto> findAllDto() {
        List<User> userList = userRepository.findAll();

        return userList.stream()
                .map(user -> mapUserToDto(user))
                .collect(Collectors.toList());

    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Page<User> findAllPaginated(Integer currentPage, Integer pageSize) {
        return userRepository.findAll(PageRequest.of(currentPage, pageSize));

    }

    @Override
    public Page<User> findStudentByNameContaining(String name, Integer currentPage, Integer pageSize) {

        List<User> userList = userRepository.findByNameContainingIgnoreCase(name);

        Page<User> page = new PageImpl<>(userList, PageRequest.of(currentPage, pageSize), userList.size());

        return page;
    }

    @Override
    public UserDto mapUserToDto(User user) {

        UserDto userDto = new UserDto();
        String[] name = user.getName().split(" ");

        // Setting up a name
        userDto.setFirstName(name[1]);
        userDto.setMiddleName(name[2]);
        userDto.setLastName(name[0].substring(0, name[0].length() - 1));

        // Setting up a student number
        userDto.setStudentNumber(user.getStudentNumber());

        // Settinghh up contact number
        userDto.setContactNumber(user.getContactNumber());

        //  Setting up an email
        userDto.setEmail(user.getEmail());

        return userDto;

    }

    @Override
    public User findByStudentId(Long studentId) {

        return userRepository
                .findById(studentId)
                .orElseThrow(() -> new RuntimeException("Did not found student id " + studentId));
    }

    private Role checkRoleExist() {

        Role role = new Role();
        role.setName("ROLE_STUDENT");
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

    @Override
    public void updateStudentDetailsById(Long studentId, UserDto userDto) {


        // Find student by student id
        User user = findByStudentId(studentId);

        // Set new name
        user.setName(userDto.getLastName().toUpperCase() + ", " +
                     userDto.getFirstName().toUpperCase() + " " +
                     userDto.getMiddleName().toUpperCase());

        // Set email
        user.setEmail(userDto.getEmail());

        // Set student number
        user.setStudentNumber(userDto.getStudentNumber());

        // Set contact number
        user.setContactNumber(userDto.getContactNumber());

        // Update the new student details
        userRepository.save(user);
    }


    @Override
    public void deleteStudentById(Long studentId) {

        // Find student by id
        User user = findByStudentId(studentId);

        // Make the role as null
        user.setRoles(null);

        // Delete student
        userRepository.deleteById(studentId);
    }
}
