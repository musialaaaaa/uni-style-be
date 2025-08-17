package org.example.uni_style_be.service.impl;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.uni_style_be.entities.Image;
import org.example.uni_style_be.entities.Product;
import org.example.uni_style_be.entities.ProductDetail;
import org.example.uni_style_be.enums.NotFoundError;
import org.example.uni_style_be.exception.ResponseException;
import org.example.uni_style_be.mapper.ProductMapper;
import org.example.uni_style_be.model.filter.ProductParam;
import org.example.uni_style_be.model.request.ProductRequest;
import org.example.uni_style_be.model.response.ProductResponse;
import org.example.uni_style_be.repositories.ProductRepository;
import org.example.uni_style_be.repositories.specification.ProductSpecification;
import org.example.uni_style_be.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductServiceImpl implements ProductService {

    private static final String PREFIX_CODE = "CL";

    ProductRepository productRepository;
    ObjectMapper objectMapper;
    ProductMapper productMapper;

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
    public void delete(Long id) {
        productRepository.deleteById(id);
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
        return productPage.map(this::enrichFilter);
    }

    private BigDecimal getMinPrice(List<ProductDetail> productDetails) {
        return productDetails.stream()
                .map(ProductDetail::getPrice)
                .filter(Objects::nonNull)
                .min(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);
    }

    private ProductResponse enrichFilter(Product product) {
        ProductResponse productResponse = productMapper.toProductResponse(product);
        List<ProductDetail> productDetails = product.getProductDetails();
        if (!CollectionUtils.isEmpty(productDetails)) {
            ProductDetail productDetail = productDetails.get(0);
            List<Image> images = productDetail.getImages();
            if (!CollectionUtils.isEmpty(images)) {
                Image image = images.get(0);
                productResponse.setImageFileName(image.getFileName());
            }

            BigDecimal price = getMinPrice(productDetails);
            productResponse.setPrice(price);
        }

        return productResponse;
    }
}
