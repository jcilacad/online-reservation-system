package com.system.reservation.online.bootstrap;

import com.system.reservation.online.entity.Role;
import com.system.reservation.online.entity.User;
import com.system.reservation.online.repository.RoleRepository;
import com.system.reservation.online.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Order(2)
public class AdminSeeder implements ApplicationListener<ContextRefreshedEvent> {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.createAdministrator();
    }

    private void createAdministrator() {
        // you can put the credentials in the application.properties, and make env.properties for security.
        String email = "sample@gmail.com";
        if (!userRepository.existsByEmail(email)) {
            User user = new User();
            user.setName("Your name");
            user.setContactNumber("09999999999");
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode("samplepassword"));
            Role role = new Role();
            role.setName("ROLE_ADMIN");
            user.setRoles(List.of(role));
            userRepository.save(user);
        }
    }
}
