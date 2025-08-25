package org.example.uni_style_be.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.uni_style_be.config.Constants;
import org.example.uni_style_be.enums.AccountType;
import org.example.uni_style_be.model.filter.AccountParam;
import org.example.uni_style_be.model.request.account.CreateAccountRequest;
import org.example.uni_style_be.model.request.account.UpdateAccountRequest;
import org.example.uni_style_be.model.request.account.UpdateMyAccountRequest;
import org.example.uni_style_be.model.response.AccountDetailResponse;
import org.example.uni_style_be.model.response.AccountFilterResponse;
import org.example.uni_style_be.model.response.ServiceResponse;
import org.example.uni_style_be.service.AccountService;
import org.example.uni_style_be.utils.PageUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
@Tag(name = "Thông tin người dùng")
public class AccountController {
    private final AccountService accountService;

    @GetMapping("/my-account")
    @Operation(summary = "Profile người dùng đang đăng nhập hiện tại")
    public ServiceResponse<AccountDetailResponse> getMyAccountDetail() {
        return ServiceResponse.ok(accountService.getMyAccountDetail());
    }

    @PutMapping("update-my-account")
    @Operation(summary = "Cập nhật profile người dùng đang đăng nhập hiện tại")
    public ServiceResponse<AccountDetailResponse> updateMyAccount(@RequestBody @Valid UpdateMyAccountRequest request) {
        return ServiceResponse.ok(accountService.updateMyAccount(request));
    }

    @PostMapping
    @Operation(summary = "Tạo tài khoản cho nhân viên")
    @PreAuthorize("hasRole('" + Constants.Roles.ACCOUNT + "')")
    public ServiceResponse<AccountDetailResponse> create(@RequestBody @Valid CreateAccountRequest request) {
        return ServiceResponse.ok(accountService.create(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Cập nhật tài khoản")
    @PreAuthorize("hasRole('" + Constants.Roles.ACCOUNT + "')")
    public ServiceResponse<AccountDetailResponse> update(
            @PathVariable Long id, @RequestBody @Valid UpdateAccountRequest request
    ) {
        return ServiceResponse.ok(accountService.update(id, request));
    }

    @GetMapping
    @Operation(summary = "Danh sách tài khoản")
    @PreAuthorize("hasRole('" + Constants.Roles.ACCOUNT + "')")
    public ServiceResponse<PageUtils<AccountFilterResponse>> filter(
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "type", required = false) AccountType type,
            Pageable pageable
    ) {
        AccountParam param = AccountParam.builder().keyword(keyword).type(type).build();
        PageUtils<AccountFilterResponse> response = new PageUtils<>(accountService.filter(param, pageable));
        return ServiceResponse.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Xoá tài khoản nhân viên")
    @PreAuthorize("hasRole('" + Constants.Roles.ACCOUNT + "')")
    public ServiceResponse<Void> delete(@PathVariable Long id) {
        return ServiceResponse.ok(accountService.delete(id));
    }

}
