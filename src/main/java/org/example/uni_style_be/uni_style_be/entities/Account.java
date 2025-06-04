package org.example.uni_style_be.uni_style_be.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Account extends BaseEntity implements UserDetails {

    @Column(nullable = false, unique = true, length = 10)
    String username;

    @Column(nullable = false)
    String password;

    @Column(nullable = false, length = 50)
    String fullName;

    @Column(nullable = false)
    String email;

    @Column(nullable = false, length = 10)
    String phone;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    Set<Token> tokens;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }
}
