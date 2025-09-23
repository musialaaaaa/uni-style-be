package org.example.uni_style_be.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.uni_style_be.enums.ProductDetailStatus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductDetail extends BaseEntity {

    @Column(nullable = false, name = "code")
    String code;

    @Column(nullable = false, name = "name")
    String name;

    @Column(nullable = false, name = "quantity")
    Integer quantity;

    @Column(nullable = false, name = "price")
    BigDecimal price;

    @Column(name = "description")
    String description;

    @Column(name = "status")
    ProductDetailStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_id")
    Material material;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "color_id")
    Color color;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "size_id")
    Size size;

    @OneToMany(mappedBy = "productDetail", cascade = CascadeType.ALL)
    List<Image> images = new ArrayList<>();

    @OneToMany(mappedBy = "productDetail", cascade = CascadeType.ALL)
    List<CartDetail> cartDetails = new ArrayList<>();

    @PrePersist
    void prePersist() {
        this.status = ProductDetailStatus.INACTIVE;
    }
}
