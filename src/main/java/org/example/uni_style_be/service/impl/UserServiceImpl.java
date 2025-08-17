package org.example.uni_style_be.service.impl;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.uni_style_be.entities.User;
import org.example.uni_style_be.enums.NotFoundError;
import org.example.uni_style_be.exception.ResponseException;
import org.example.uni_style_be.model.filter.UserParam;
import org.example.uni_style_be.model.request.UserRequest;
import org.example.uni_style_be.model.response.UserResponse;
import org.example.uni_style_be.repositories.UserRepository;
import org.example.uni_style_be.repositories.specification.UserSpecification;
import org.example.uni_style_be.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Page<User> filter(UserParam param, Pageable pageable) {
        return userRepository.findAll(UserSpecification.filterSpec(param), pageable);
    }

    @Override
    public User findById(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new ResponseException(NotFoundError.USER_NOT_FOUND));
    }

    @Override
    @Transactional
    public UserResponse create(UserRequest userRequest) {
        User user = objectMapper.convertValue(userRequest, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return objectMapper.convertValue(userRepository.save(user), UserResponse.class);
    }

    @Override
    @Transactional
    public UserResponse update(Long id, UserRequest userRequest) throws JsonMappingException {
        User user = findById(id);
        objectMapper.updateValue(user, userRequest);
        return objectMapper.convertValue(userRepository.save(user), UserResponse.class);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        User user = findById(id);
        user.setIsDeleted(true);
        userRepository.save(user);
    }
}
