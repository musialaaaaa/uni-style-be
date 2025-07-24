package org.example.uni_style_be.config;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.uni_style_be.entities.Account;
import org.example.uni_style_be.entities.Role;
import org.example.uni_style_be.repositories.AccountRepository;
import org.example.uni_style_be.repositories.RoleRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class DataInitializer implements ApplicationRunner {

    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin";
    private static final String ADMIN_FULL_NAME = "Admin";
    private static final String ADMIN_EMAIL = "admin@unistyle.com";
    private static final String ADMIN_PHONE = "0123456789";

    AccountRepository accountRepository;
    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        log.info("Initializing admin account...");
        initializeAdminAccount();
        log.info("Admin account initialization completed successfully");
    }

    private void initializeAdminAccount() {
        Account adminAccount = accountRepository.findByUsername(ADMIN_USERNAME)
                .orElseGet(this::createAdminAccount);

        // Chỉ cập nhật roles nếu account chưa có roles hoặc roles đã thay đổi
        List<Role> allRoles = roleRepository.findAll();
        if (adminAccount.getRoles() == null || adminAccount.getRoles().size() != allRoles.size()) {
            adminAccount.setRoles(new HashSet<>(allRoles));
            accountRepository.save(adminAccount);
            log.info("Updated admin account with {} roles", allRoles.size());
        } else {
            log.info("Admin account already has all roles, skipping update");
        }
    }

    private Account createAdminAccount() {
        log.info("Creating new admin account");

        Account newAdmin = Account.builder()
                .username(ADMIN_USERNAME)
                .password(passwordEncoder.encode(ADMIN_PASSWORD))
                .fullName(ADMIN_FULL_NAME)
                .email(ADMIN_EMAIL)
                .phone(ADMIN_PHONE)
                .build();

        return accountRepository.save(newAdmin);
    }
}