package org.example.uni_style_be.service.impl;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.uni_style_be.entities.Image;
import org.example.uni_style_be.entities.ProductDetail;
import org.example.uni_style_be.enums.NotFoundError;
import org.example.uni_style_be.exception.ResponseException;
import org.example.uni_style_be.mapper.ProductDetailMapper;
import org.example.uni_style_be.model.filter.ProductDetailParam;
import org.example.uni_style_be.model.request.ProductDetailRequest;
import org.example.uni_style_be.model.response.ProductDetailResponse;
import org.example.uni_style_be.repositories.ImageRepository;
import org.example.uni_style_be.repositories.ProductDetailRepository;
import org.example.uni_style_be.repositories.specification.ProductDetailSpecification;
import org.example.uni_style_be.service.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductDetailServiceImpl implements ProductDetailService {

    private static final String PREFIX_CODE = "PRD";

    ProductDetailRepository productDetailRepository;
    ProductService productService;
    SizeService sizeService;
    ColorService colorService;
    MaterialService materialService;
    CategoryService categoryService;
    ImageRepository imageRepository;
    ObjectMapper objectMapper;
    ProductDetailMapper productDetailMapper;

    @Override
    @Transactional
    public ProductDetailResponse create(ProductDetailRequest productDetailRequest) {
        ProductDetail productDetail = objectMapper.convertValue(productDetailRequest, ProductDetail.class);
        productDetail.setCode(PREFIX_CODE + productDetailRepository.getNextSeq());
        setEntityRel(productDetail, productDetailRequest);

        List<Image> images = imageRepository.findAllById(productDetailRequest.getImageIds());
        productDetail.setImages(images);

        ProductDetail productDetailSaved = productDetailRepository.save(productDetail);

        return productDetailMapper.toProductDetailResponse(productDetailSaved);
    }

    @Override
    @Transactional
    public ProductDetailResponse update(Long id, ProductDetailRequest productDetailRequest) throws JsonMappingException {
        ProductDetail prDetail = findById(id);
        objectMapper.updateValue(prDetail, productDetailRequest);
        setEntityRel(prDetail, productDetailRequest);

        List<Image> images = imageRepository.findAllById(productDetailRequest.getImageIds());
        prDetail.setImages(images);

        return objectMapper.convertValue(productDetailRepository.save(prDetail), ProductDetailResponse.class);
    }

    @Override
    public Page<ProductDetailResponse> filter(ProductDetailParam param, Pageable pageable) {
        Specification<ProductDetail> prDetailSpec = ProductDetailSpecification.filterSpec(param);
        Page<ProductDetail> productDetailPage = productDetailRepository.findAll(prDetailSpec, pageable);
        return productDetailPage.map(product -> objectMapper.convertValue(product, ProductDetailResponse.class));
    }

    @Override
    public void delete(Long id) {
        productDetailRepository.deleteById(id);
    }

    @Override
    public ProductDetail findById(Long id) {
        return productDetailRepository
                .findById(id)
                .orElseThrow(() -> new ResponseException(NotFoundError.PRODUCT_DETAIL_NOT_FOUND));
    }

    private void setEntityRel(ProductDetail productDetail, ProductDetailRequest productDetailRequest) {
        productDetail.setProduct(productService.findById(productDetailRequest.getProductId()));
        productDetail.setCategory(categoryService.findById(productDetailRequest.getCategoryId()));
        productDetail.setMaterial(materialService.findById(productDetailRequest.getMaterialId()));
        productDetail.setSize(sizeService.findByID(productDetailRequest.getSizeId()));
        productDetail.setColor(colorService.findById(productDetailRequest.getColorId()));
    }


}
