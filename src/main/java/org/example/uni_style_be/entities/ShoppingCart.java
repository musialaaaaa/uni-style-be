package org.example.uni_style_be.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "shoppingCart")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShoppingCart extends BaseEntity {

    @Column(nullable = false)
    String name;
    @Column(nullable = false)
    Status status;

    public enum Status {
        ACTIVE,
        ORDERED,
        EXPIRED
    }
    @Column(nullable = false, name = "is_deleted")
    Boolean isDeleted =Boolean.FALSE;



}
