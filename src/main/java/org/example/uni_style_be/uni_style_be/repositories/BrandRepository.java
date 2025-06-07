package org.example.uni_style_be.uni_style_be.repositories;

import org.example.uni_style_be.uni_style_be.entities.Brand;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface BrandRepository
    extends CrudRepository<Brand, Long>, JpaSpecificationExecutor<Brand> {
  @Query("SELECT COALESCE(MAX(b.id), 0) + 1 FROM Brand b")
  Long getNextSeq();
}
