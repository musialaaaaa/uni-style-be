package org.example.uni_style_be.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.uni_style_be.enums.AccountType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Account extends BaseEntity implements UserDetails {

    @Column(nullable = false, unique = true, length = 50)
    String username;

    @Column(nullable = false)
    String password;

    @Column(nullable = false, length = 50)
    String fullName;

    String email;

    String phone;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    AccountType type;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    Set<Token> tokens;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "account_role",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    Set<Role> roles = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (roles == null) {
            return Set.of();
        }

        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getCode()))
                .collect(Collectors.toSet());
    }
}
