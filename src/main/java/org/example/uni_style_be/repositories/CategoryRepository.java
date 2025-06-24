package org.example.uni_style_be.repositories;

import org.example.uni_style_be.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>, JpaSpecificationExecutor<Category> {
    @Query("SELECT COALESCE(MAX(c.id), 0) + 1 FROM Category c")
    Long getNextSeq();
}
