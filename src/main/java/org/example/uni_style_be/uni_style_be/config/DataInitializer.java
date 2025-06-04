package org.example.uni_style_be.uni_style_backend.config;
;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.uni_style_be.uni_style_backend.entities.Account;
import org.example.uni_style_be.uni_style_backend.repositories.AccountRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class DataInitializer implements ApplicationRunner {
    AccountRepository accountRepository;
    PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) {
        if (accountRepository.existsByUsernameIgnoreCase("admin")) {
            return;
        }
        Account account = Account.builder()
                .username("ADMIN")
                .password(passwordEncoder.encode("admin"))
                .fullName("Admin")
                .email("admin@unistyle.com")
                .phone("0123456789")
                .build();
        accountRepository.save(account);
        log.info("Default admin account created successfully");
    }
}
