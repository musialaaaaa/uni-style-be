package org.example.uni_style_be.uni_style_be.service;

import com.fasterxml.jackson.databind.JsonMappingException;
import org.example.uni_style_be.uni_style_be.entities.Color;
import org.example.uni_style_be.uni_style_be.model.filter.ColorParam;
import org.example.uni_style_be.uni_style_be.model.request.ColorRequest;
import org.example.uni_style_be.uni_style_be.model.response.ColorResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ColorService {

  ColorResponse create(ColorRequest colorRequest);

  ColorResponse update(Long id, ColorRequest colorRequest) throws JsonMappingException;

  void delete(Long id);

  Color findById(Long id);

  Page<ColorResponse> filter(ColorParam param, Pageable pageable);
}
