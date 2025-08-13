package org.example.uni_style_be.repositories;

import org.example.uni_style_be.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {

    Optional<Image> findByFileName(String fileName);

}