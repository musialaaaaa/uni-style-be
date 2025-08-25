package org.example.uni_style_be.service;

import org.example.uni_style_be.model.filter.AccountParam;
import org.example.uni_style_be.model.request.account.CreateAccountRequest;
import org.example.uni_style_be.model.request.account.UpdateAccountRequest;
import org.example.uni_style_be.model.request.account.UpdateMyAccountRequest;
import org.example.uni_style_be.model.response.AccountDetailResponse;
import org.example.uni_style_be.model.response.AccountFilterResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AccountService {
    AccountDetailResponse getMyAccountDetail();

    AccountDetailResponse updateMyAccount(UpdateMyAccountRequest request);

    AccountDetailResponse create(CreateAccountRequest request);

    AccountDetailResponse update(Long id, UpdateAccountRequest request);

    AccountDetailResponse detail(Long id);

    Page<AccountFilterResponse> filter(AccountParam param, Pageable pageable);

    Void delete(Long id);
}
