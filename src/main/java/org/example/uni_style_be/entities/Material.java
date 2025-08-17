package org.example.uni_style_be.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Material extends BaseEntity {

    @Column(nullable = false, name = "code")
    String code;

    @Column(nullable = false, name = "name")
    String name;

}
