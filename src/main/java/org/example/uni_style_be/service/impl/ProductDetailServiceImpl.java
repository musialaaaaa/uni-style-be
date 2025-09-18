package org.example.uni_style_be.service.impl;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.uni_style_be.entities.Image;
import org.example.uni_style_be.entities.Product;
import org.example.uni_style_be.entities.ProductDetail;
import org.example.uni_style_be.enums.InvalidInputError;
import org.example.uni_style_be.enums.NotFoundError;
import org.example.uni_style_be.exception.ResponseException;
import org.example.uni_style_be.mapper.ProductDetailMapper;
import org.example.uni_style_be.model.filter.ProductDetailParam;
import org.example.uni_style_be.model.request.ProductDetailRequest;
import org.example.uni_style_be.model.response.ProductDetailResponse;
import org.example.uni_style_be.repositories.ImageRepository;
import org.example.uni_style_be.repositories.OrderDetailRepository;
import org.example.uni_style_be.repositories.ProductDetailRepository;
import org.example.uni_style_be.repositories.ProductRepository;
import org.example.uni_style_be.repositories.specification.ProductDetailSpecification;
import org.example.uni_style_be.service.ColorService;
import org.example.uni_style_be.service.MaterialService;
import org.example.uni_style_be.service.ProductDetailService;
import org.example.uni_style_be.service.SizeService;
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
    SizeService sizeService;
    ColorService colorService;
    MaterialService materialService;
    ImageRepository imageRepository;
    ObjectMapper objectMapper;
    ProductDetailMapper productDetailMapper;
    ProductRepository productRepository;
    OrderDetailRepository orderDetailRepository;

    @Override
    @Transactional
    public ProductDetailResponse create(ProductDetailRequest productDetailRequest) {
        ProductDetail productDetail = objectMapper.convertValue(productDetailRequest, ProductDetail.class);
        productDetail.setCode(PREFIX_CODE + productDetailRepository.getNextSeq());
        setEntityRel(productDetail, productDetailRequest);

        List<Image> images = imageRepository.findAllById(productDetailRequest.getImageIds());
        productDetail.setImages(images);
        images.forEach(image -> image.setProductDetail(productDetail));

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
        return productDetailPage.map(productDetailMapper::toProductDetailResponse);
    }

    @Override
    public void delete(Long id) {
        if (orderDetailRepository.existsByProductDetail_Id(id)) {
            throw new ResponseException(InvalidInputError.PRODUCT_DETAIL_HAS_ORDER);
        }
        productDetailRepository.deleteById(id);
    }

    @Override
    public ProductDetail findById(Long id) {
        return productDetailRepository
                .findById(id)
                .orElseThrow(() -> new ResponseException(NotFoundError.PRODUCT_DETAIL_NOT_FOUND));
    }

    private void setEntityRel(ProductDetail productDetail, ProductDetailRequest productDetailRequest) {
        Product product = productRepository.findById(productDetailRequest.getProductId())
                .orElseThrow(() -> new ResponseException(InvalidInputError.PRODUCT_NOT_FOUND));

        productDetail.setProduct(product);
        productDetail.setMaterial(materialService.findById(productDetailRequest.getMaterialId()));
        productDetail.setSize(sizeService.findByID(productDetailRequest.getSizeId()));
        productDetail.setColor(colorService.findById(productDetailRequest.getColorId()));
    }

}
