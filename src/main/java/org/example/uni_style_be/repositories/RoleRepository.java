package org.example.uni_style_be.repositories;

import org.example.uni_style_be.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {
    List<Role> findByAccounts_Id(Long id);
}
