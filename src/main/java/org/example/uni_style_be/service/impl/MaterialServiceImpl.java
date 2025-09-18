package org.example.uni_style_be.service.impl;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.uni_style_be.entities.Material;
import org.example.uni_style_be.enums.InvalidInputError;
import org.example.uni_style_be.enums.NotFoundError;
import org.example.uni_style_be.exception.ResponseException;
import org.example.uni_style_be.model.filter.MaterialParam;
import org.example.uni_style_be.model.request.MaterialRequest;
import org.example.uni_style_be.model.response.MaterialResponse;
import org.example.uni_style_be.repositories.MaterialRepository;
import org.example.uni_style_be.repositories.ProductDetailRepository;
import org.example.uni_style_be.repositories.specification.MaterialSpecification;
import org.example.uni_style_be.service.MaterialService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MaterialServiceImpl implements MaterialService {

    private static final String PREFIX_CODE = "MT";

    MaterialRepository materialRepository;
    ObjectMapper objectMapper;
    ProductDetailRepository productDetailRepository;

    @Override
    @Transactional
    public MaterialResponse create(MaterialRequest materialRequest) {
        Material material = objectMapper.convertValue(materialRequest, Material.class);
        material.setCode(PREFIX_CODE + materialRepository.getNextSeq());
        return objectMapper.convertValue(materialRepository.save(material), MaterialResponse.class);
    }

    @Override
    public Material findById(Long id) {
        return materialRepository
                .findById(id)
                .orElseThrow(() -> new ResponseException(NotFoundError.MATERIAL_NOT_FOUND));
    }

    @Override
    @Transactional
    public MaterialResponse update(Long id, MaterialRequest materialRequest) throws JsonMappingException {
        Material material = findById(id);

        objectMapper.updateValue(material, materialRequest);
        return objectMapper.convertValue(materialRepository.save(material), MaterialResponse.class);
    }

    @Override
    public void delete(Long id) {
        if (productDetailRepository.existsByMaterial_Id(id)) {
            throw new ResponseException(InvalidInputError.MATERIAL_HAS_PRODUCT);
        }
        materialRepository.deleteById(id);
    }

    @Override
    public Page<MaterialResponse> filter(MaterialParam param, Pageable pageable) {
        Specification<Material> materialSpec = MaterialSpecification.filterSpec(param);
        Page<Material> materialPage = materialRepository.findAll(materialSpec, pageable);
        return materialPage.map(material -> objectMapper.convertValue(material, MaterialResponse.class));
    }

}
