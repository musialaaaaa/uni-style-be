package org.example.uni_style_be.controller;

import com.fasterxml.jackson.databind.JsonMappingException;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.uni_style_be.entities.Customer;
import org.example.uni_style_be.model.filter.CustomerParam;
import org.example.uni_style_be.model.request.CustomerRequest;
import org.example.uni_style_be.model.response.CustomerResponse;
import org.example.uni_style_be.service.CustomerService;
import org.example.uni_style_be.utils.PageUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
@Tag(name = "Api Khách hàng")
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping
    public PageUtils<Customer> filter(CustomerParam param, Pageable pageable) {
        return new PageUtils<>(customerService.filter(param, pageable));
    }

    @PostMapping
    public CustomerResponse create(@Valid @RequestBody CustomerRequest request) {
        return customerService.create(request);
    }

    @PutMapping("/{id}")
    public CustomerResponse update(@PathVariable Long id, @Valid @RequestBody CustomerRequest customerRequest) throws JsonMappingException {
        return customerService.update(id, customerRequest);
    }

    @GetMapping("/{id}")
    public Customer findById(@PathVariable Long id) {
        return customerService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        customerService.delete(id);
    }
}