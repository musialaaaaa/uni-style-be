package org.example.uni_style_be.uni_style_be.service.impl;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

  private final ObjectMapper objectMapper;
  private final String PREFIX_CODE = "BR";
  @Override
  @Transactional
  public BrandReponse create(BrandRequest brandRequest) {
    Brand brand = objectMapper.convertValue(brandRequest, Brand.class);
    brand.setCode(PREFIX_CODE + brandRepository.getNextSeq());
    return objectMapper.convertValue(brandRepository.save(brand), BrandReponse.class);
  }

  @Override
  @Transactional
  public BrandReponse update(Long id, BrandRequest brandRequest) throws JsonMappingException {
    Brand brand = findById(id);
    objectMapper.updateValue(brand, brandRequest);
    return objectMapper.convertValue(brandRepository.save(brand), BrandReponse.class);
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
  public Page<BrandReponse> filter(BrandParam param, Pageable pageable) {
    Specification<Brand> brandSpec = BrandSpecification.filterSpec(param);
    Page<Brand> brandPage = brandRepository.findAll(brandSpec, pageable);
    return brandPage.map(brand -> objectMapper.convertValue(brand,BrandReponse.class));
  }

}
