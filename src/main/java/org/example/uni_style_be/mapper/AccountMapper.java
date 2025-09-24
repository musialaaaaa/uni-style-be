package org.example.uni_style_be.mapper;

import org.example.uni_style_be.entities.Account;
import org.example.uni_style_be.model.request.RegisterRequest;
import org.example.uni_style_be.model.request.account.CreateAccountRequest;
import org.example.uni_style_be.model.request.account.UpdateAccountRequest;
import org.example.uni_style_be.model.request.account.UpdateMyAccountRequest;
import org.example.uni_style_be.model.response.AccountDetailResponse;
import org.example.uni_style_be.model.response.AccountFilterResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface AccountMapper {

    Account toAccount(RegisterRequest request);

    AccountDetailResponse toAccountDetailResponse(Account account);

    void toAccount(UpdateMyAccountRequest request, @MappingTarget Account account);

    @Mapping(target = "password", ignore = true)
    void toAccount(UpdateAccountRequest request, @MappingTarget Account account);

    @Mapping(target = "password", ignore = true)
    Account toAccount(CreateAccountRequest request);

    @Mapping(target = "id", source = "id")
    AccountFilterResponse toAccountFilterResponse(Account account);
}
