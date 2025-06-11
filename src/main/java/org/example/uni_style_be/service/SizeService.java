package org.example.uni_style_be.service;

import org.example.uni_style_be.entities.Size;
import org.example.uni_style_be.model.filter.SizeParam;
import org.example.uni_style_be.model.request.SizeRequest;
import org.example.uni_style_be.model.response.SizeResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SizeService {

  SizeResponse create(SizeRequest sizeRequest);

  SizeResponse update(Long id, SizeRequest sizeRequest);

  void delete(Long id);

  Size findByID(Long id);

  Page<Size> filter(SizeParam param, Pageable pageable);
}
