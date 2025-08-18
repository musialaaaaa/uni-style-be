package org.example.uni_style_be.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "product")
public class Product extends BaseEntity {

    @Column(nullable = false, name = "code")
    String code;

    @Column(nullable = false, name = "name")
    String name;

    @Column(name = "description", columnDefinition = "text")
    String description;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    List<ProductDetail> productDetails;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    Category category;
}
