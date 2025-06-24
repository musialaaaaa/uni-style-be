package org.example.uni_style_be.service;

import com.fasterxml.jackson.databind.JsonMappingException;
import org.example.uni_style_be.entities.Customer;
import org.example.uni_style_be.model.filter.CustomerParam;
import org.example.uni_style_be.model.request.CustomerRequest;
import org.example.uni_style_be.model.response.CustomerResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerService {

    Page<Customer> filter(CustomerParam param, Pageable pageable);

    Customer findById(Long id);

    CustomerResponse create(CustomerRequest customerRequest);

    CustomerResponse update(Long id, CustomerRequest customerRequest) throws JsonMappingException;

    void delete(Long id);
}
