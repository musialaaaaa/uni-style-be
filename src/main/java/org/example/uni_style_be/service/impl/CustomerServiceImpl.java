package org.example.uni_style_be.service.impl;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.uni_style_be.entities.Customer;
import org.example.uni_style_be.enums.NotFoundError;
import org.example.uni_style_be.exception.ResponseException;
import org.example.uni_style_be.model.filter.CustomerParam;
import org.example.uni_style_be.model.request.CustomerRequest;
import org.example.uni_style_be.model.response.CustomerResponse;
import org.example.uni_style_be.repositories.CustomerRepository;
import org.example.uni_style_be.repositories.specification.CustomerSpecification;
import org.example.uni_style_be.service.CustomerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final ObjectMapper objectMapper;
    private final String PREFIX_CODE = "CUS";

    @Override
    public Page<Customer> filter(CustomerParam param, Pageable pageable) {
        return customerRepository.findAll(CustomerSpecification.filterSpec(param), pageable);
    }

    @Override
    public Customer findById(Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new ResponseException(NotFoundError.CUSTOMER_NOT_FOUND));
    }

    @Override
    @Transactional
    public CustomerResponse create(CustomerRequest customerRequest) {
        Customer customer = objectMapper.convertValue(customerRequest, Customer.class);
        customer.setCode(PREFIX_CODE + customerRepository.getNextSeq());
        return objectMapper.convertValue(customerRepository.save(customer), CustomerResponse.class);
    }

    @Override
    @Transactional
    public CustomerResponse update(Long id, CustomerRequest customerRequest) throws JsonMappingException {
        Customer customer = findById(id);
        customer.setCode(customer.getCode());
        objectMapper.updateValue(customer, customerRequest);
        return objectMapper.convertValue(customerRepository.save(customer), CustomerResponse.class);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Customer customer = findById(id);
        customer.setIsDeleted(true);
        customerRepository.save(customer);
    }
}
