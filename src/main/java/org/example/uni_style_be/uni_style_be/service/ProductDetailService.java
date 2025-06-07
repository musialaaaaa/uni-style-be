package org.example.uni_style_be.uni_style_be.service;

import org.example.uni_style_be.uni_style_be.entities.ProductDetail;
import org.example.uni_style_be.uni_style_be.model.filter.ProductDetailParam;
import org.example.uni_style_be.uni_style_be.model.request.ProductDetailRequest;
import org.example.uni_style_be.uni_style_be.model.response.ProductDetailResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductDetailService {
  ProductDetailResponse create(ProductDetailRequest productDetailRequest);

  ProductDetailResponse update(Long id, ProductDetailRequest productDetailRequest);

  void delete(Long id);

  ProductDetail findById(Long id);

  Page<ProductDetail> filter(ProductDetailParam Param, Pageable pageable);
}
