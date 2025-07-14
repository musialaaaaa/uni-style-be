package org.example.uni_style_be.controller;

import com.fasterxml.jackson.databind.JsonMappingException;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.uni_style_be.entities.User;
import org.example.uni_style_be.model.filter.UserParam;
import org.example.uni_style_be.model.request.UserRequest;
import org.example.uni_style_be.model.response.UserResponse;
import org.example.uni_style_be.service.UserService;
import org.example.uni_style_be.utils.PageUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
@Tag(name = "Api Khách hàng")
public class CustomerController {
    private final UserService userService;

    @GetMapping
    public PageUtils<User> filter(UserParam param, Pageable pageable) {
        return new PageUtils<>(userService.filter(param, pageable));
    }

    @PostMapping
    public UserResponse create(@Valid @RequestBody UserRequest request) {
        return userService.create(request);
    }

    @PutMapping("/{id}")
    public UserResponse update(@PathVariable Long id, @Valid @RequestBody UserRequest userRequest) throws JsonMappingException {
        return userService.update(id, userRequest);
    }

    @GetMapping("/{id}")
    public User findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        userService.delete(id);
    }
}