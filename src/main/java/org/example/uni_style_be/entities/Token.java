package org.example.uni_style_be.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Token extends BaseEntity {

    @Column(unique = true, nullable = false)
    String token;

    boolean expired;

    boolean revoked;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    Account account;

    @PrePersist
    protected void onCreate() {
        this.expired = false;
        this.revoked = false;
    }
}
