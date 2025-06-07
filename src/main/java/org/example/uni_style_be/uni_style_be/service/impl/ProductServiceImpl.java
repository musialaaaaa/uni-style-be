package org.example.uni_style_be.uni_style_be.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.uni_style_be.uni_style_be.entities.Product;
import org.example.uni_style_be.uni_style_be.enums.NotFoundError;
import org.example.uni_style_be.uni_style_be.exception.ResponseException;
import org.example.uni_style_be.uni_style_be.model.filter.ProductParam;
import org.example.uni_style_be.uni_style_be.model.request.ProductRequest;
import org.example.uni_style_be.uni_style_be.model.response.ProductResponse;
import org.example.uni_style_be.uni_style_be.repositories.ProductRepository;
import org.example.uni_style_be.uni_style_be.repositories.specification.ProductSpecification;
import org.example.uni_style_be.uni_style_be.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;

  @Override
  @Transactional
  public ProductResponse create(ProductRequest productRequest) {
    Product product =
        Product.builder()
            .name(productRequest.getName())
            .isDeleted(false)
            .description(productRequest.getDescription())
            .code("SP" + productRepository.getNextSeq())
            .build();
    productRepository.save(product);
    return mapToResponse(product);
  }

  @Override
  @Transactional
  public ProductResponse update(Long id, ProductRequest productRequest) {
    Product product = findById(id);
    product.setName(productRequest.getName());
    product.setDescription(productRequest.getDescription());
    productRepository.save(product);
    return mapToResponse(product);
  }

  @Override
  @Transactional
  public void delete(Long id) {
    Product product = findById(id);
    product.setIsDeleted(true);
    productRepository.save(product);
  }

  @Override
  public Product findById(Long id) {
    return productRepository
        .findById(id)
        .orElseThrow(() -> new ResponseException(NotFoundError.PRODUCT_NOT_FOUND));
  }

  @Override
  public Page<Product> filter(ProductParam param, Pageable pageable) {
    Specification<Product> productSpec = ProductSpecification.filterSpec(param);
    return productRepository.findAll(productSpec, pageable);
  }

  private ProductResponse mapToResponse(Product product) {
    return ProductResponse.builder()
        .code(product.getCode())
        .id(product.getId())
        .name(product.getName())
        .description(product.getDescription())
        .createdAt(product.getCreatedAt())
        .updatedAt(product.getUpdatedAt())
        .isDeleted(product.getIsDeleted())
        .updatedBy(product.getUpdatedBy())
        .createdBy(product.getCreatedBy())
        .build();
  }
}
