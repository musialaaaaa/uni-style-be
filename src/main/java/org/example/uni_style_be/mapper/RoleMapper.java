package org.example.uni_style_be.mapper;

import org.example.uni_style_be.entities.Role;
import org.example.uni_style_be.model.response.RoleResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface RoleMapper {
    List<RoleResponse> toRoleResponse(List<Role> roles);
}
