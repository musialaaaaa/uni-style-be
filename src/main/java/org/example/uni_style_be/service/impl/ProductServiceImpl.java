package org.example.uni_style_be.service.impl;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.uni_style_be.entities.Product;
import org.example.uni_style_be.enums.NotFoundError;
import org.example.uni_style_be.exception.ResponseException;
import org.example.uni_style_be.model.filter.ProductParam;
import org.example.uni_style_be.model.request.ProductRequest;
import org.example.uni_style_be.model.response.ProductResponse;
import org.example.uni_style_be.repositories.ProductRepository;
import org.example.uni_style_be.service.ProductService;
import org.example.uni_style_be.repositories.specification.ProductSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;
  private final ObjectMapper objectMapper;
  private final String PREFIX_CODE = "CL";

  @Override
  @Transactional
  public ProductResponse create(ProductRequest productRequest) {
    Product product = objectMapper.convertValue(productRequest, Product.class);
    product.setCode(PREFIX_CODE + productRepository.getNextSeq());
    return objectMapper.convertValue(productRepository.save(product), ProductResponse.class);
  }

  @Override
  @Transactional
  public ProductResponse update(Long id, ProductRequest productRequest) throws JsonMappingException {
    Product product = findById(id);
    objectMapper.updateValue(product, productRequest);
    return objectMapper.convertValue(productRepository.save(product), ProductResponse.class);
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
  public Page<ProductResponse> filter(ProductParam param, Pageable pageable) {
    Specification<Product> productSpec = ProductSpecification.filterSpec(param);
    Page<Product> productPage = productRepository.findAll(productSpec, pageable);
    return productPage.map(product -> objectMapper.convertValue(product, ProductResponse.class));
  }
}
