package org.example.uni_style_be.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "roles")
public class Role extends BaseEntity {

    @Column(nullable = false, unique = true)
    String code;

    @Column(nullable = false)
    String name;

    @ManyToMany(mappedBy = "roles")
    Set<Account> accounts = new HashSet<>();

}
