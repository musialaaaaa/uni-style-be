package org.example.uni_style_be.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.uni_style_be.entities.Color;
import org.example.uni_style_be.enums.NotFoundError;
import org.example.uni_style_be.exception.ResponseException;
import org.example.uni_style_be.model.filter.ColorParam;
import org.example.uni_style_be.model.request.ColorRequest;
import org.example.uni_style_be.model.response.ColorResponse;
import org.example.uni_style_be.repositories.ColorRepository;
import org.example.uni_style_be.repositories.specification.ColorSpecification;
import org.example.uni_style_be.service.ColorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ColorServiceImpl implements ColorService {
  private final ColorRepository colorRepository;

  @Override
  @Transactional
  public ColorResponse create(ColorRequest colorRequest) {
    Color color =
        Color.builder()
            .isDeleted(false)
            .code("CL" + colorRepository.getNextSeq())
            .name(colorRequest.getName())
            .build();
    colorRepository.save(color);
    return mapToResponse(color);
  }

  @Override
  @Transactional
  public ColorResponse update(Long id, ColorRequest colorRequest) {
    Color color = findById(id);
    color.setName(colorRequest.getName());
    colorRepository.save(color);
    return mapToResponse(color);
  }

  @Override
  @Transactional
  public void delete(Long id) {
    Color color = findById(id);
    color.setIsDeleted(true);
    colorRepository.save(color);
  }

  @Override
  public Color findById(Long id) {
    return colorRepository
        .findById(id)
        .orElseThrow(() -> new ResponseException(NotFoundError.COLOR_NOT_FOUND));
  }

  @Override
  public Page<Color> filter(ColorParam param, Pageable pageable) {
    Specification<Color> colorSpec = ColorSpecification.filterSpec(param);
    return colorRepository.findAll(colorSpec, pageable);
  }

  private ColorResponse mapToResponse(Color color) {
    return ColorResponse.builder()
        .id(color.getId())
        .name(color.getName())
        .code(color.getCode())
        .isDeleted(color.getIsDeleted())
        .createdBy(color.getCreatedBy())
        .createdAt(color.getCreatedAt())
        .updatedBy(color.getUpdatedBy())
        .updatedAt(color.getUpdatedAt())
        .build();
  }
}
