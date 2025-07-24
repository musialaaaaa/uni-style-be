package org.example.uni_style_be.service;

import org.example.uni_style_be.model.request.SetRoleRequest;
import org.example.uni_style_be.model.response.RoleResponse;

import java.util.List;

public interface RoleService {
    List<RoleResponse> getList();

    Void setRole(SetRoleRequest request);

    List<RoleResponse> getRoleByAccount(Long accountId);
}
