package org.example.uni_style_be.uni_style_backend.config;

import org.example.uni_style_be.uni_style_backend.entities.Account;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Objects;
import java.util.Optional;

@Configuration
@EnableTransactionManagement
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class PersistenceConfig {

    @Bean
    AuditorAware<String> auditorProvider() {
        return new AuditorAwareConfig();
    }

    public static class AuditorAwareConfig implements AuditorAware<String> {
        @Override
        @NonNull
        public Optional<String> getCurrentAuditor() {
            SecurityContext securityContext = SecurityContextHolder.getContext();
            Authentication authentication = securityContext.getAuthentication();
            if (
                    Objects.isNull(authentication)
                    || Objects.isNull(authentication.getPrincipal())
                    || authentication instanceof AnonymousAuthenticationToken
                    || !authentication.isAuthenticated()
            ) {
                return Optional.of(Constants.SYSTEM);
            }
            Account account = (Account) authentication.getPrincipal();
            return Optional.ofNullable(account.getUsername());
        }
    }
}
