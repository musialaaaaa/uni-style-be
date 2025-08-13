package org.example.uni_style_be.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "images")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Image extends BaseEntity {

    @Column(nullable = false)
    String originalName;

    @Column(nullable = false, unique = true)
    String fileName;

    @Column(nullable = false)
    String filePath;

    @Column(nullable = false)
    String contentType;

    @Column(nullable = false)
    Long fileSize;

}
