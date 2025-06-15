package org.example.uni_style_be.uni_style_be.service;

import com.fasterxml.jackson.databind.JsonMappingException;
import org.example.uni_style_be.uni_style_be.entities.ProductDetail;
import org.example.uni_style_be.uni_style_be.model.filter.ProductDetailParam;
import org.example.uni_style_be.uni_style_be.model.request.ProductDetailRequest;
import org.example.uni_style_be.uni_style_be.model.response.ProductDetailResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductDetailService {
  ProductDetailResponse create(ProductDetailRequest productDetailRequest);

  ProductDetailResponse update(Long id, ProductDetailRequest productDetailRequest) throws JsonMappingException;

  void delete(Long id);

  ProductDetail findById(Long id);

  Page<ProductDetailResponse> filter(ProductDetailParam Param, Pageable pageable);
}
