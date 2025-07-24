package org.example.uni_style_be.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.uni_style_be.entities.Account;
import org.example.uni_style_be.entities.Role;
import org.example.uni_style_be.enums.InvalidInputError;
import org.example.uni_style_be.exception.ResponseException;
import org.example.uni_style_be.mapper.RoleMapper;
import org.example.uni_style_be.model.request.SetRoleRequest;
import org.example.uni_style_be.model.response.RoleResponse;
import org.example.uni_style_be.repositories.AccountRepository;
import org.example.uni_style_be.repositories.RoleRepository;
import org.example.uni_style_be.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleServiceImpl implements RoleService {
    RoleRepository roleRepository;
    RoleMapper roleMapper;
    AccountRepository accountRepository;

    @Override
    public List<RoleResponse> getList() {
        return roleMapper.toRoleResponse(roleRepository.findAll());
    }

    @Override
    public Void setRole(SetRoleRequest request) {
        Account account = accountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new ResponseException(InvalidInputError.ACCOUNT_NOT_FOUND));

        Set<Role> roles = new HashSet<>(roleRepository.findAllById(request.getRoleIds()));
        if (roles.isEmpty() || roles.size() != request.getRoleIds().size()) {
            throw new ResponseException(InvalidInputError.ROLE_NOT_FOUND);
        }

        account.setRoles(roles);
        accountRepository.save(account);
        return null;
    }

    @Override
    public List<RoleResponse> getRoleByAccount(Long accountId) {
        List<Role> roles = roleRepository.findByAccounts_Id(accountId);
        return roleMapper.toRoleResponse(roles);
    }

}
