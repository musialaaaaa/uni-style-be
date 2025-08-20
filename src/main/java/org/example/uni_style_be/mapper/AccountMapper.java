package org.example.uni_style_be.mapper;

import org.example.uni_style_be.entities.Account;
import org.example.uni_style_be.model.request.RegisterRequest;
import org.mapstruct.Mapper;

@Mapper
public interface AccountMapper {

    Account toAccount(RegisterRequest request);
}
