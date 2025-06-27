package org.example.uni_style_be.repositories;

import org.example.uni_style_be.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
