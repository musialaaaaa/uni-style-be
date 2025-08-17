package org.example.uni_style_be.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Where;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "product")
@Where(clause = "is_deleted = false")
public class Product extends BaseEntity {

    @Column(nullable = false, name = "code")
    String code;

    @Column(nullable = false, name = "name")
    String name;

    @Column(name = "description")
    String description;

    @Column(nullable = false, name = "is_deleted")
    Boolean isDeleted = Boolean.FALSE;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    List<ProductDetail> productDetails;
}
