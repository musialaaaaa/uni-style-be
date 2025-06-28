package org.example.uni_style_be.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartDetail extends BaseEntity{

    @Column(nullable = false)
    Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    Cart cart;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "cart_product_detail",
        joinColumns = @JoinColumn(name = "cart_detail_id"),
        inverseJoinColumns = @JoinColumn(name = "product_detail_id")
    )
    List<ProductDetail> productDetails;
}
