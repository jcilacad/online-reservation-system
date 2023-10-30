package com.system.reservation.online.security;

import com.system.reservation.online.entity.Role;
import com.system.reservation.online.entity.User;
import com.system.reservation.online.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<User> result = userRepository.findByEmail(email);
        User user;
        if (result.isPresent()) {
            user = result.get();
            String userEmail = user.getEmail();
            String userPassword = user.getPassword();
            List<Role> userRoles = user.getRoles();

            return new org.springframework.security.core.userdetails.User(userEmail, userPassword, mapRolesToAuthorities(userRoles));


        } else {
            throw new UsernameNotFoundException("Invalid Username or Password");
        }
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities (List<Role> roles) {

        Collection<? extends GrantedAuthority> mapRoles = roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

        return mapRoles;
    }
}
