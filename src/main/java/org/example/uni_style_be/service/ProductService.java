package org.example.uni_style_be.service;

import com.fasterxml.jackson.databind.JsonMappingException;
import org.example.uni_style_be.entities.Product;
import org.example.uni_style_be.model.filter.ProductParam;
import org.example.uni_style_be.model.request.ProductRequest;
import org.example.uni_style_be.model.response.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    ProductResponse create(ProductRequest productRequest);

    ProductResponse update(Long id, ProductRequest productRequest) throws JsonMappingException;

    void delete(Long id);

    Product findById(Long id);

    Page<ProductResponse> filter(ProductParam param, Pageable pageable);
}
