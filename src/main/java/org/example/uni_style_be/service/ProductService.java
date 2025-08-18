package org.example.uni_style_be.service;

import com.fasterxml.jackson.databind.JsonMappingException;
import org.example.uni_style_be.entities.Product;
import org.example.uni_style_be.model.filter.ProductParam;
import org.example.uni_style_be.model.request.ProductRequest;
import org.example.uni_style_be.model.response.ProductDetailShopResponse;
import org.example.uni_style_be.model.response.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    ProductResponse create(ProductRequest productRequest);

    ProductResponse update(Long id, ProductRequest productRequest) throws JsonMappingException;

    Void delete(Long id);

    ProductResponse detail(Long id);

    Page<ProductResponse> filter(ProductParam param, Pageable pageable);

    ProductDetailShopResponse detailShop(Long id);
}
