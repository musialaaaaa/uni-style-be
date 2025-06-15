package org.example.uni_style_be.service.impl;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.uni_style_be.entities.Color;
import org.example.uni_style_be.enums.NotFoundError;
import org.example.uni_style_be.exception.ResponseException;
import org.example.uni_style_be.model.filter.ColorParam;
import org.example.uni_style_be.model.request.ColorRequest;
import org.example.uni_style_be.model.response.ColorResponse;
import org.example.uni_style_be.repositories.ColorRepository;
import org.example.uni_style_be.service.ColorService;
import org.example.uni_style_be.repositories.specification.ColorSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ColorServiceImpl implements ColorService {
  private final ColorRepository colorRepository;
  private final ObjectMapper objectMapper;
  private final String PREFIX_CODE = "CL";
  @Override
  @Transactional
  public ColorResponse create(ColorRequest colorRequest) {
    Color color = objectMapper.convertValue(colorRequest, Color.class);
    color.setCode(PREFIX_CODE + colorRepository.getNextSeq());
    return objectMapper.convertValue(colorRepository.save(color), ColorResponse.class);
  }

  @Override
  @Transactional
  public ColorResponse update(Long id, ColorRequest colorRequest) throws JsonMappingException {
    Color color = findById(id);
    objectMapper.updateValue(color, colorRequest);
    return objectMapper.convertValue(colorRepository.save(color), ColorResponse.class);
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
  public Page<ColorResponse> filter(ColorParam param, Pageable pageable) {
    Specification<Color> colorSpec = ColorSpecification.filterSpec(param);
    Page<Color> colorPage = colorRepository.findAll(colorSpec, pageable);
    return colorPage.map(color -> objectMapper.convertValue(color, ColorResponse.class));
  }
}
