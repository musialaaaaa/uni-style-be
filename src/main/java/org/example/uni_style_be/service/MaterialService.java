package org.example.uni_style_be.service;

import org.example.uni_style_be.entities.Material;
import org.example.uni_style_be.model.filter.MaterialParam;
import org.example.uni_style_be.model.request.MaterialRequest;
import org.example.uni_style_be.model.response.MaterialResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MaterialService {

  MaterialResponse create(MaterialRequest materialRequest);

  MaterialResponse update(Long id, MaterialRequest materialRequest);

  void delete(Long id);

  Material findById(Long id);

  Page<Material> filter(MaterialParam param, Pageable pageable);
}
