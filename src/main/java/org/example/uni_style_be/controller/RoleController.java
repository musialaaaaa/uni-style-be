package org.example.uni_style_be.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.uni_style_be.config.Constants;
import org.example.uni_style_be.model.request.SetRoleRequest;
import org.example.uni_style_be.model.response.RoleResponse;
import org.example.uni_style_be.model.response.ServiceResponse;
import org.example.uni_style_be.service.RoleService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@Tag(name = "API Quyền")
@PreAuthorize("hasRole('" + Constants.Roles.ROLE + "')")
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    @Operation(summary = "Lấy danh sách tất cả các quyền")
    public ServiceResponse<List<RoleResponse>> getList() {
        return ServiceResponse.ok(roleService.getList());
    }

    @PostMapping("/set-roles")
    @Operation(summary = "Gán quyền cho tài khoản")
    public ServiceResponse<Void> setRole(@RequestBody @Valid SetRoleRequest request) {
        return ServiceResponse.ok(roleService.setRole(request));
    }

    @GetMapping("/account/{accountId}")
    @Operation(summary = "Lấy danh sách quyền đang gán cho tài khoản")
    public ServiceResponse<List<RoleResponse>> getRoleByAccount(@PathVariable Long accountId) {
        return ServiceResponse.ok(roleService.getRoleByAccount(accountId));
    }
}
