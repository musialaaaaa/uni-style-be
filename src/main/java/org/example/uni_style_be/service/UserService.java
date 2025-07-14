package org.example.uni_style_be.service;

import com.fasterxml.jackson.databind.JsonMappingException;
import org.example.uni_style_be.entities.User;
import org.example.uni_style_be.model.filter.UserParam;
import org.example.uni_style_be.model.request.UserRequest;
import org.example.uni_style_be.model.response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    Page<User> filter(UserParam param, Pageable pageable);

    User findById(Long id);

    UserResponse create(UserRequest userRequest);

    UserResponse update(Long id, UserRequest userRequest) throws JsonMappingException;

    void delete(Long id);
}
