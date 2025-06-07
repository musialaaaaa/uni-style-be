package org.example.uni_style_be.uni_style_be.service;

import org.example.uni_style_be.uni_style_be.entities.Product;
import org.example.uni_style_be.uni_style_be.model.filter.ProductParam;
import org.example.uni_style_be.uni_style_be.model.request.ProductRequest;
import org.example.uni_style_be.uni_style_be.model.response.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
  ProductResponse create(ProductRequest productRequest);

  ProductResponse update(Long id, ProductRequest productRequest);

  void delete(Long id);

  Product findById(Long id);

  Page<Product> filter(ProductParam param, Pageable pageable);
}
