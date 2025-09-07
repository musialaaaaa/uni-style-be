package org.example.uni_style_be.utils;

import lombok.experimental.UtilityClass;
import org.example.uni_style_be.entities.Account;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;
import java.util.Optional;

@UtilityClass
public class SecurityUtils {

    public static Optional<Account> getCurrentAccount() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (
                Objects.isNull(authentication)
                        || Objects.isNull(authentication.getPrincipal())
                        || authentication instanceof AnonymousAuthenticationToken
                        || !authentication.isAuthenticated()
        ) {
            return Optional.empty();
        }
        Account account = (Account) authentication.getPrincipal();
        return Optional.ofNullable(account);
    }

    public static Optional<Long> getCurrentAccountId() {
        return getCurrentAccount().map(Account::getId);
    }

    public static Optional<String> getCurrentUsername() {
        return getCurrentAccount().map(Account::getUsername);
    }
}
