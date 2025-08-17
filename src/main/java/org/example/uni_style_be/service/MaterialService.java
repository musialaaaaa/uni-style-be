package org.example.uni_style_be.service;

import com.fasterxml.jackson.databind.JsonMappingException;
import org.example.uni_style_be.entities.Material;
import org.example.uni_style_be.model.filter.MaterialParam;
import org.example.uni_style_be.model.request.MaterialRequest;
import org.example.uni_style_be.model.response.MaterialResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MaterialService {

    MaterialResponse create(MaterialRequest materialRequest);

    MaterialResponse update(Long id, MaterialRequest materialRequest) throws JsonMappingException;

    void delete(Long id);

    Material findById(Long id);

    Page<MaterialResponse> filter(MaterialParam param, Pageable pageable);
}
