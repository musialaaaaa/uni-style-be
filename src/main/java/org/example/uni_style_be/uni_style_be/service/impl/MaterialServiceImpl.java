package org.example.uni_style_be.uni_style_be.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.uni_style_be.uni_style_be.entities.Material;
import org.example.uni_style_be.uni_style_be.enums.NotFoundError;
import org.example.uni_style_be.uni_style_be.exception.ResponseException;
import org.example.uni_style_be.uni_style_be.model.filter.MaterialParam;
import org.example.uni_style_be.uni_style_be.model.request.MaterialRequest;
import org.example.uni_style_be.uni_style_be.model.response.MaterialResponse;
import org.example.uni_style_be.uni_style_be.repositories.MaterialRepository;
import org.example.uni_style_be.uni_style_be.repositories.specification.MaterialSpecification;
import org.example.uni_style_be.uni_style_be.service.MaterialService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MaterialServiceImpl implements MaterialService {

  private final MaterialRepository materialRepository;

  @Override
  @Transactional
  public MaterialResponse create(MaterialRequest materialRequest) {
    Material material =
        Material.builder()
            .name(materialRequest.getName())
            .isDeleted(false)
            .code("MT" + materialRepository.getNextSeq())
            .build();
    materialRepository.save(material);
    return mapToResponse(material);
  }

  @Override
  public Material findById(Long id) {
    return materialRepository
        .findById(id)
        .orElseThrow(() -> new ResponseException(NotFoundError.MATERIAL_NOT_FOUND));
  }

  @Override
  @Transactional
  public MaterialResponse update(Long id, MaterialRequest materialRequest) {
    Material material = findById(id);
    material.setName(materialRequest.getName());
    materialRepository.save(material);
    return mapToResponse(material);
  }

  @Override
  @Transactional
  public void delete(Long id) {
    Material material = findById(id);
    material.setIsDeleted(true);
    materialRepository.save(material);
  }

  @Override
  public Page<Material> filter(MaterialParam param, Pageable pageable) {
    Specification<Material> materialSpec = MaterialSpecification.filterSpec(param);
    return materialRepository.findAll(materialSpec, pageable);
  }

  private MaterialResponse mapToResponse(Material material) {
    return MaterialResponse.builder()
        .code(material.getCode())
        .id(material.getId())
        .name(material.getName())
        .createdAt(material.getCreatedAt())
        .updatedAt(material.getUpdatedAt())
        .isDeleted(material.getIsDeleted())
        .updatedBy(material.getUpdatedBy())
        .createdBy(material.getCreatedBy())
        .build();
  }
}
