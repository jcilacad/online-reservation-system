package com.system.reservation.online.bootstrap;

import com.system.reservation.online.entity.Role;
import com.system.reservation.online.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.system.reservation.online.enums.Role.ROLE_ADMIN;
import static com.system.reservation.online.enums.Role.ROLE_STUDENT;

@Component
@RequiredArgsConstructor
@Order(1)
public class RoleSeeder implements ApplicationListener<ContextRefreshedEvent> {

    private final RoleRepository roleRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.createRoles();
    }

    private void createRoles() {
        // Create student role
        Role studentRole = new Role();
        studentRole.setName("ROLE_STUDENT");

        // Create admin role
        Role adminRole = new Role();
        adminRole.setName("ROLE_ADMIN");

        // Save the roles in database
        roleRepository.saveAll(List.of(studentRole, adminRole));
    }
}
