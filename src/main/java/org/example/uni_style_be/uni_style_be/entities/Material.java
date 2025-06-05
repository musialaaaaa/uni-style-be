package org.example.uni_style_be.uni_style_be.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Where;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "material")
@Where(clause = "is_deleted = false")
public class Material extends BaseEntity {

  @Column(nullable = false, name = "code")
  String code;

  @Column(nullable = false, name = "name")
  String name;

  @Column(nullable = false, name = "is_deleted")
  Boolean isDeleted;
}
