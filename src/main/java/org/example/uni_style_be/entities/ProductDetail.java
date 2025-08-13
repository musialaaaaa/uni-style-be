package org.example.uni_style_be.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "product_detail")
@Where(clause = "is_deleted = false")
public class ProductDetail extends BaseEntity {

    @Column(nullable = false, name = "code")
    String code;

    @Column(nullable = false, name = "name")
    String name;

    @Column(nullable = false, name = "quantity")
    Integer quantity;

    @Column(nullable = false, name = "price")
    BigDecimal price;

    @Column(nullable = false, name = "image")
    String image;

    @Column(name = "description")
    String description;

    @Column(nullable = false, name = "is_deleted")
    Boolean isDeleted = Boolean.FALSE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @NotFound(action = NotFoundAction.IGNORE)
    Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @NotFound(action = NotFoundAction.IGNORE)
    Category Category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_id")
    @NotFound(action = NotFoundAction.IGNORE)
    Material material;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    @NotFound(action = NotFoundAction.IGNORE)
    Brand brand;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "color_id")
    @NotFound(action = NotFoundAction.IGNORE)
    Color color;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "size_id")
    @NotFound(action = NotFoundAction.IGNORE)
    Size size;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "product_detail_image",
            joinColumns = @JoinColumn(name = "product_detail_id"),
            inverseJoinColumns = @JoinColumn(name = "image_id")
    )
    Set<Image> images = new HashSet<>();

}
