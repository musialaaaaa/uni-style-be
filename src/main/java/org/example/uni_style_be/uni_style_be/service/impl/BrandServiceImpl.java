package org.example.uni_style_be.uni_style_be.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.uni_style_be.uni_style_be.entities.Brand;
import org.example.uni_style_be.uni_style_be.enums.NotFoundError;
import org.example.uni_style_be.uni_style_be.exception.ResponseException;
import org.example.uni_style_be.uni_style_be.model.filter.BrandParam;
import org.example.uni_style_be.uni_style_be.model.request.BrandRequest;
import org.example.uni_style_be.uni_style_be.model.response.BrandReponse;
import org.example.uni_style_be.uni_style_be.repositories.BrandRepository;
import org.example.uni_style_be.uni_style_be.repositories.specification.BrandSpecification;
import org.example.uni_style_be.uni_style_be.service.BrandService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

  private final BrandRepository brandRepository;

  @Override
  @Transactional
  public BrandReponse create(BrandRequest brandRequest) {
    Brand brand =
        Brand.builder()
            .isDeleted(false)
            .code("BR" + brandRepository.getNextSeq())
            .name(brandRequest.getName())
            .build();
    brandRepository.save(brand);
    return mapToResponse(brand);
  }

  @Override
  @Transactional
  public BrandReponse update(Long id, BrandRequest brandRequest) {
    Brand brand = findById(id);
    brand.setName(brandRequest.getName());
    brandRepository.save(brand);
    return mapToResponse(brand);
  }

  @Override
  @Transactional
  public void delete(Long id) {
    Brand brand = findById(id);
    brand.setIsDeleted(true);
    brandRepository.save(brand);
  }

  @Override
  public Brand findById(Long id) {
    return brandRepository
        .findById(id)
        .orElseThrow(() -> new ResponseException(NotFoundError.BRAND_NOT_FOUND));
  }

  @Override
  public Page<Brand> filter(BrandParam param, Pageable pageable) {
    Specification<Brand> brandSpec = BrandSpecification.filterSpec(param);
    return brandRepository.findAll(brandSpec, pageable);
  }

  private BrandReponse mapToResponse(Brand brand) {
    return BrandReponse.builder()
        .id(brand.getId())
        .name(brand.getName())
        .isDeleted(brand.getIsDeleted())
        .code(brand.getCode())
        .createdAt(brand.getCreatedAt())
        .updatedAt(brand.getUpdatedAt())
        .createdBy(brand.getCreatedBy())
        .updatedBy(brand.getUpdatedBy())
        .build();
  }
}
