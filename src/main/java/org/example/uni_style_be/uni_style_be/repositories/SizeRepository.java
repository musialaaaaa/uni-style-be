package org.example.uni_style_be.uni_style_be.repositories;

import org.example.uni_style_be.uni_style_be.entities.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SizeRepository extends JpaRepository<Size, Long>, JpaSpecificationExecutor<Size> {

  @Query("SELECT COALESCE(MAX(s.id), 0) + 1 FROM Size s")
  Long getNextSeq();
}
