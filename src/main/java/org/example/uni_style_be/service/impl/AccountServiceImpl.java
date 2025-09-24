package org.example.uni_style_be.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.uni_style_be.entities.Account;
import org.example.uni_style_be.entities.Role;
import org.example.uni_style_be.enums.AccountType;
import org.example.uni_style_be.enums.InvalidInputError;
import org.example.uni_style_be.enums.NotFoundError;
import org.example.uni_style_be.enums.UnauthorizedError;
import org.example.uni_style_be.exception.ResponseException;
import org.example.uni_style_be.mapper.AccountMapper;
import org.example.uni_style_be.model.filter.AccountParam;
import org.example.uni_style_be.model.request.account.CreateAccountRequest;
import org.example.uni_style_be.model.request.account.UpdateAccountRequest;
import org.example.uni_style_be.model.request.account.UpdateMyAccountRequest;
import org.example.uni_style_be.model.response.AccountDetailResponse;
import org.example.uni_style_be.model.response.AccountFilterResponse;
import org.example.uni_style_be.repositories.AccountRepository;
import org.example.uni_style_be.repositories.RoleRepository;
import org.example.uni_style_be.repositories.specification.AccountSpecification;
import org.example.uni_style_be.service.AccountService;
import org.example.uni_style_be.utils.SecurityUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountServiceImpl implements AccountService {
    AccountRepository accountRepository;
    AccountMapper accountMapper;
    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;

    @Override
    public AccountDetailResponse getMyAccountDetail() {
        Account account = SecurityUtils.getCurrentAccount()
                .orElseThrow(() -> new ResponseException(UnauthorizedError.UNAUTHORIZED));
        return accountMapper.toAccountDetailResponse(account);
    }

    @Override
    public AccountDetailResponse updateMyAccount(UpdateMyAccountRequest request) {
        Account account = SecurityUtils.getCurrentAccount()
                .orElseThrow(() -> new ResponseException(UnauthorizedError.UNAUTHORIZED));
        accountMapper.toAccount(request, account);
        Account updatedAccount = accountRepository.save(account);
        return accountMapper.toAccountDetailResponse(updatedAccount);
    }

    @Override
    public AccountDetailResponse create(CreateAccountRequest request) {
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new ResponseException(InvalidInputError.PASSWORD_NOT_MATCH);
        }

        if (accountRepository.existsByUsernameIgnoreCase(request.getUsername())) {
            throw new ResponseException(InvalidInputError.ACCOUNT_EXIST);
        }

        Account account = accountMapper.toAccount(request);
        account.setPassword(passwordEncoder.encode(request.getPassword()));
        account.setType(AccountType.CUSTOMER);

        List<Role> roles = roleRepository.findAllById(request.getRoleIds());
        account.setRoles(new HashSet<>(roles));

        Account createdAccount = accountRepository.save(account);
        return accountMapper.toAccountDetailResponse(createdAccount);
    }

    @Override
    public AccountDetailResponse update(Long id, UpdateAccountRequest request) {
        Account account = accountRepository.findById(id).orElseThrow(
                () -> new ResponseException(NotFoundError.ACCOUNT_NOT_FOUND)
        );
        accountMapper.toAccount(request, account);
        if (StringUtils.hasText(request.getPassword())) {
            if (!request.getPassword().equals(request.getConfirmPassword())) {
                throw new ResponseException(InvalidInputError.PASSWORD_NOT_MATCH);
            }
            String passwordEncoded = this.passwordEncoder.encode(request.getPassword());
            account.setPassword(passwordEncoded);
        }

        List<Role> roles = roleRepository.findAllById(request.getRoleIds());
        account.setRoles(new HashSet<>(roles));

        Account updatedAccount = accountRepository.save(account);
        return accountMapper.toAccountDetailResponse(updatedAccount);
    }

    @Override
    public AccountDetailResponse detail(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(
                () -> new ResponseException(NotFoundError.ACCOUNT_NOT_FOUND)
        );
        return accountMapper.toAccountDetailResponse(account);
    }

    @Override
    public Page<AccountFilterResponse> filter(AccountParam param, Pageable pageable) {
        Specification<Account> accountSpecification = AccountSpecification.filterSpec(param);
        Page<Account> accountPage = accountRepository.findAll(accountSpecification, pageable);
        return accountPage.map(accountMapper::toAccountFilterResponse);
    }

    @Override
    public Void delete(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(
                () -> new ResponseException(NotFoundError.ACCOUNT_NOT_FOUND)
        );
        if (!AccountType.STAFF.equals(account.getType())) {
            throw new ResponseException(InvalidInputError.ONLY_DELETE_STAFF_ACCOUNT);
        }
        accountRepository.delete(account);
        return null;
    }

}
