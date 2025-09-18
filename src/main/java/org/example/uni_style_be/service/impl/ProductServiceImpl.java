package org.example.uni_style_be.service.impl;

import com.fasterxml.jackson.databind.JsonMappingException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.uni_style_be.entities.Category;
import org.example.uni_style_be.entities.Image;
import org.example.uni_style_be.entities.Product;
import org.example.uni_style_be.entities.ProductDetail;
import org.example.uni_style_be.enums.InvalidInputError;
import org.example.uni_style_be.enums.NotFoundError;
import org.example.uni_style_be.exception.ResponseException;
import org.example.uni_style_be.mapper.ProductDetailMapper;
import org.example.uni_style_be.mapper.ProductMapper;
import org.example.uni_style_be.model.filter.ProductParam;
import org.example.uni_style_be.model.request.ProductRequest;
import org.example.uni_style_be.model.response.ProductDetailResponse;
import org.example.uni_style_be.model.response.ProductDetailShopResponse;
import org.example.uni_style_be.model.response.ProductResponse;
import org.example.uni_style_be.repositories.CategoryRepository;
import org.example.uni_style_be.repositories.OrderDetailRepository;
import org.example.uni_style_be.repositories.ProductRepository;
import org.example.uni_style_be.repositories.specification.ProductSpecification;
import org.example.uni_style_be.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
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
    ProductMapper productMapper;
    CategoryRepository categoryRepository;
    ProductDetailMapper productDetailMapper;
    OrderDetailRepository orderDetailRepository;

    @Override
    public ProductResponse create(ProductRequest rq) {

        Category category = categoryRepository.findById(rq.getCategoryId())
                .orElseThrow(() -> new ResponseException(InvalidInputError.CATEGORY_NOT_FOUND));

        Product product = productMapper.toProduct(rq);
        product.setCode(PREFIX_CODE + productRepository.getNextSeq());
        product.setCategory(category);

        Product productSaved = productRepository.save(product);

        return productMapper.toProductResponse(productSaved);
    }

    @Override
    public ProductResponse update(Long id, ProductRequest rq) throws JsonMappingException {
        Product product = findById(id);

        Category category = categoryRepository.findById(rq.getCategoryId())
                .orElseThrow(() -> new ResponseException(InvalidInputError.CATEGORY_NOT_FOUND));

        productMapper.toProduct(product, rq);
        product.setCategory(category);

        Product productSaved = productRepository.save(product);
        return productMapper.toProductResponse(productSaved);
    }

    @Override
    public Void delete(Long id) {
        if (orderDetailRepository.existsByProductDetail_Product_Id(id)) {
            throw new ResponseException(InvalidInputError.PRODUCT_HAS_ORDER);
        }
        productRepository.deleteById(id);
        return null;
    }

    @Override
    public ProductResponse detail(Long id) {
        Product product = findById(id);
        return productMapper.toProductResponse(product);
    }

    @Override
    public Page<ProductResponse> filter(ProductParam param, Pageable pageable) {
        Specification<Product> productSpec = ProductSpecification.filterSpec(param);
        Page<Product> productPage = productRepository.findAll(productSpec, pageable);
        return productPage.map(this::enrichFilter);
    }

    @Override
    public ProductDetailShopResponse detailShop(Long id) {
        Product product = findById(id);
        ProductDetailShopResponse response = productMapper.toProductDetailShopResponse(product);
        List<ProductDetailResponse> productDetails = productDetailMapper.toProductDetailResponse(product.getProductDetails());
        response.setProductDetails(productDetails);
        return response;
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

    private Product findById(Long id) {
        return productRepository
                .findById(id)
                .orElseThrow(() -> new ResponseException(NotFoundError.PRODUCT_NOT_FOUND));
    }

}
