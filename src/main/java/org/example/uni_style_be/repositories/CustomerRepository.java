package org.example.uni_style_be.repositories;

import org.example.uni_style_be.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {

    @Query("SELECT COALESCE(MAX(c.id), 0) + 1 FROM Customer c")
    Long getNextSeq();
}
