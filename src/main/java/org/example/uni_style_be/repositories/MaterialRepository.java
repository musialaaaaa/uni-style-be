package org.example.uni_style_be.repositories;

import org.example.uni_style_be.entities.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialRepository
        extends JpaRepository<Material, Long>, JpaSpecificationExecutor<Material> {

    @Query("SELECT COALESCE(MAX(m.id), 0) + 1 FROM Material m")
    Long getNextSeq();

    //  @Query(
    //      "SELECT m FROM Material m WHERE "
    //          + "(:#{#param.name} IS NULL OR LOWER(m.name) LIKE LOWER(CONCAT('%', :#{#param.name},
    // '%'))) AND "
    //          + "(:#{#param.code} IS NULL OR LOWER(m.code) LIKE LOWER(CONCAT('%', :#{#param.code},
    // '%')))")
    //  Page<Material> filter(MaterialParam param, Pageable pageable);

}
