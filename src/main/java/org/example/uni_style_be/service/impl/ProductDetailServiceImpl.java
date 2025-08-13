package org.example.uni_style_be.service.impl;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.uni_style_be.entities.Image;
import org.example.uni_style_be.entities.ProductDetail;
import org.example.uni_style_be.enums.NotFoundError;
import org.example.uni_style_be.exception.ResponseException;
import org.example.uni_style_be.model.filter.ProductDetailParam;
import org.example.uni_style_be.model.request.ProductDetailRequest;
import org.example.uni_style_be.model.response.ProductDetailResponse;
import org.example.uni_style_be.repositories.ImageRepository;
import org.example.uni_style_be.repositories.ProductDetailRepository;
import org.example.uni_style_be.service.*;
import org.example.uni_style_be.repositories.specification.ProductDetailSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductDetailServiceImpl implements ProductDetailService {

  private final ProductDetailRepository productDetailRepository;
  private final ProductService productService;
  private final SizeService sizeService;
  private final ColorService colorService;
  private final BrandService brandService;
  private final MaterialService materialService;
  private final CategoryService categoryService;
  private final ImageRepository imageRepository;

  private final ObjectMapper objectMapper;
  private final String PREFIX_CODE = "PRD";

  @Override
  @Transactional
  public ProductDetailResponse create(ProductDetailRequest productDetailRequest) {
    ProductDetail productDetail = objectMapper.convertValue(productDetailRequest, ProductDetail.class);
    productDetail.setCode(PREFIX_CODE +productDetailRepository.getNextSeq());
    setEntityRel(productDetail, productDetailRequest);

    List<Image> images = imageRepository.findAllById(productDetailRequest.getImageIds());
    productDetail.setImages(new HashSet<>(images));

    return objectMapper.convertValue(productDetailRepository.save(productDetail), ProductDetailResponse.class);
  }

  @Override
  @Transactional
  public ProductDetailResponse update(Long id, ProductDetailRequest productDetailRequest) throws JsonMappingException {
    ProductDetail prDetail = findById(id);
    objectMapper.updateValue(prDetail, productDetailRequest);
    setEntityRel(prDetail, productDetailRequest);

      List<Image> images = imageRepository.findAllById(productDetailRequest.getImageIds());
      prDetail.setImages(new HashSet<>(images));

    return objectMapper.convertValue(productDetailRepository.save(prDetail), ProductDetailResponse.class);
  }

  @Override
  public Page<ProductDetailResponse> filter(ProductDetailParam param, Pageable pageable) {
    Specification<ProductDetail> prDetailSpec = ProductDetailSpecification.filterSpec(param);
    Page<ProductDetail> productDetailPage = productDetailRepository.findAll(prDetailSpec,pageable);
    return productDetailPage.map(product -> objectMapper.convertValue(product, ProductDetailResponse.class));
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

  private void setEntityRel (ProductDetail productDetail, ProductDetailRequest productDetailRequest){
    productDetail.setProduct(productService.findById(productDetailRequest.getProductId()));
    productDetail.setCategory(categoryService.findById(productDetailRequest.getCategoryId()));
    productDetail.setBrand(brandService.findById(productDetailRequest.getBrandId()));
    productDetail.setMaterial(materialService.findById(productDetailRequest.getMaterialId()));
    productDetail.setSize(sizeService.findByID(productDetailRequest.getSizeId()));
    productDetail.setColor(colorService.findById(productDetailRequest.getColorId()));
  }


}
