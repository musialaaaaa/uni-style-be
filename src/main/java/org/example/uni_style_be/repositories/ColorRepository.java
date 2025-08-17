package org.example.uni_style_be.repositories;

import org.example.uni_style_be.entities.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ColorRepository
        extends JpaRepository<Color, Long>, JpaSpecificationExecutor<Color> {
    @Query("SELECT COALESCE(MAX(c.id), 0) + 1 FROM Color c")
    Long getNextSeq();
}
