package org.example.uni_style_be.uni_style_be.service;

import org.example.uni_style_be.uni_style_be.entities.Brand;
import org.example.uni_style_be.uni_style_be.model.filter.BrandParam;
import org.example.uni_style_be.uni_style_be.model.request.BrandRequest;
import org.example.uni_style_be.uni_style_be.model.response.BrandReponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BrandService {

  BrandReponse create(BrandRequest brandRequest);

  BrandReponse update(Long id, BrandRequest brandRequest);

  void delete(Long id);

  Brand findById(Long id);

  Page<Brand> filter(BrandParam param, Pageable pageable);
}
