package org.example.uni_style_be.uni_style_be.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.uni_style_be.uni_style_be.entities.ProductDetail;
import org.example.uni_style_be.uni_style_be.enums.NotFoundError;
import org.example.uni_style_be.uni_style_be.exception.ResponseException;
import org.example.uni_style_be.uni_style_be.model.filter.ProductDetailParam;
import org.example.uni_style_be.uni_style_be.model.request.ProductDetailRequest;
import org.example.uni_style_be.uni_style_be.model.response.ProductDetailResponse;
import org.example.uni_style_be.uni_style_be.repositories.ProductDetailRepository;
import org.example.uni_style_be.uni_style_be.repositories.specification.ProductDetailSpecification;
import org.example.uni_style_be.uni_style_be.service.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductDetailServiceImpl implements ProductDetailService {

  private final ProductDetailRepository productDetailRepository;
  private final ProductService productService;
  private final SizeService sizeService;
  private final ColorService colorService;
  private final BrandService brandService;
  private final MaterialService materialService;

  @Override
  @Transactional
  public ProductDetailResponse create(ProductDetailRequest productDetailRequest) {
    ProductDetail productDetail =
        ProductDetail.builder()
            .name(productDetailRequest.getName())
            .image(productDetailRequest.getImage())
            .description(productDetailRequest.getDescription())
            .price(productDetailRequest.getPrice())
            .quantity(productDetailRequest.getQuantity())
            .isDeleted(false)
            .code("PD" + productDetailRepository.getNextSeq())
            .size(sizeService.findByID(productDetailRequest.getSizeId()))
            .color(colorService.findById(productDetailRequest.getColorId()))
            .brand(brandService.findById(productDetailRequest.getBrandId()))
            .material(materialService.findById(productDetailRequest.getMaterialId()))
            .product(productService.findById(productDetailRequest.getProductId()))
            .build();
    return mapToResponse(productDetailRepository.save(productDetail));
  }

  @Override
  @Transactional
  public ProductDetailResponse update(Long id, ProductDetailRequest productDetailRequest) {
    ProductDetail prDetail = findById(id);
    prDetail.setName(productDetailRequest.getName());
    prDetail.setImage(productDetailRequest.getImage());
    prDetail.setDescription(productDetailRequest.getDescription());
    prDetail.setPrice(productDetailRequest.getPrice());
    prDetail.setQuantity(productDetailRequest.getQuantity());
    prDetail.setProduct(productService.findById(productDetailRequest.getProductId()));
    prDetail.setBrand(brandService.findById(productDetailRequest.getBrandId()));
    prDetail.setMaterial(materialService.findById(productDetailRequest.getMaterialId()));
    prDetail.setSize(sizeService.findByID(productDetailRequest.getSizeId()));
    prDetail.setColor(colorService.findById(productDetailRequest.getColorId()));
    return mapToResponse(productDetailRepository.save(prDetail));
  }

  @Override
  public Page<ProductDetail> filter(ProductDetailParam param, Pageable pageable) {
    Specification<ProductDetail> prDetailSpec = ProductDetailSpecification.filterSpec(param);
    return productDetailRepository.findAll(prDetailSpec, pageable);
  }

  @Override
  @Transactional
  public void delete(Long id) {
    ProductDetail prDetail = findById(id);
    prDetail.setIsDeleted(true);
    productDetailRepository.save(prDetail);
  }

  @Override
  public ProductDetail findById(Long id) {
    return productDetailRepository
        .findById(id)
        .orElseThrow(() -> new ResponseException(NotFoundError.PRODUCT_DETAIL_NOT_FOUND));
  }

  private ProductDetailResponse mapToResponse(ProductDetail productDetail) {
    return ProductDetailResponse.builder()
        .id(productDetail.getId())
        .name(productDetail.getName())
        .image(productDetail.getImage())
        .description(productDetail.getDescription())
        .price(productDetail.getPrice())
        .quantity(productDetail.getQuantity())
        .color(productDetail.getColor())
        .brand(productDetail.getBrand())
        .material(productDetail.getMaterial())
        .product(productDetail.getProduct())
        .isDeleted(productDetail.getIsDeleted())
        .createdBy(productDetail.getCreatedBy())
        .createdAt(productDetail.getCreatedAt())
        .updatedBy(productDetail.getUpdatedBy())
        .updatedAt(productDetail.getUpdatedAt())
        .build();
  }
}
