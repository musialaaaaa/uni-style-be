package org.example.uni_style_be.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cart")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Cart extends BaseEntity {

    @Column(nullable = false, name = "account_id", unique = true)
    Long accountId;
}
