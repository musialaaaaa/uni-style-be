package org.example.uni_style_be.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.uni_style_be.entities.Size;
import org.example.uni_style_be.enums.NotFoundError;
import org.example.uni_style_be.exception.ResponseException;
import org.example.uni_style_be.model.filter.SizeParam;
import org.example.uni_style_be.model.request.SizeRequest;
import org.example.uni_style_be.model.response.SizeResponse;
import org.example.uni_style_be.repositories.SizeRepository;
import org.example.uni_style_be.repositories.specification.SizeSpecification;
import org.example.uni_style_be.service.SizeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SizeServiceImpl implements SizeService {
  private final SizeRepository sizeRepository;

  @Override
  @Transactional
  public SizeResponse create(SizeRequest sizeRequest) {
    Size size =
        Size.builder()
            .name(sizeRequest.getName())
            .isDeleted(false)
            .code("SZ" + sizeRepository.getNextSeq())
            .build();
    sizeRepository.save(size);
    return mapToResponse(size);
  }

  @Override
  @Transactional
  public SizeResponse update(Long id, SizeRequest sizeRequest) {
    Size size = findByID(id);
    size.setName(sizeRequest.getName());
    sizeRepository.save(size);
    return mapToResponse(size);
  }

  @Override
  @Transactional
  public void delete(Long id) {
    Size size = findByID(id);
    size.setIsDeleted(true);
    sizeRepository.save(size);
  }

  @Override
  public Size findByID(Long id) {
    return sizeRepository
        .findById(id)
        .orElseThrow(() -> new ResponseException(NotFoundError.SIZE_NOT_FOUND));
  }

  @Override
  public Page<Size> filter(SizeParam param, Pageable pageable) {
    Specification<Size> sizeSpec = SizeSpecification.filterSpec(param);
    return sizeRepository.findAll(sizeSpec, pageable);
  }

  private SizeResponse mapToResponse(Size size) {
    return SizeResponse.builder()
        .id(size.getId())
        .name(size.getName())
        .code(size.getCode())
        .isDeleted(size.getIsDeleted())
        .createdBy(size.getCreatedBy())
        .createdAt(size.getCreatedAt())
        .updatedBy(size.getUpdatedBy())
        .updatedAt(size.getUpdatedAt())
        .build();
  }
}
