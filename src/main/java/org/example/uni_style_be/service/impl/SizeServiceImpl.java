package org.example.uni_style_be.service.impl;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
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
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SizeServiceImpl implements SizeService {

    private static final String PREFIX_CODE = "SZ";

    SizeRepository sizeRepository;
    ObjectMapper objectMapper;

    @Override
    @Transactional
    public SizeResponse create(SizeRequest sizeRequest) {
        Size size = objectMapper.convertValue(sizeRequest, Size.class);
        size.setCode(PREFIX_CODE + sizeRepository.getNextSeq());
        return objectMapper.convertValue(sizeRepository.save(size), SizeResponse.class);
    }

    @Override
    @Transactional
    public SizeResponse update(Long id, SizeRequest sizeRequest) throws JsonMappingException {
        Size size = findByID(id);
        objectMapper.updateValue(size, sizeRequest);
        return objectMapper.convertValue(sizeRepository.save(size), SizeResponse.class);
    }

    @Override
    public void delete(Long id) {
        sizeRepository.deleteById(id);
    }

    @Override
    public Size findByID(Long id) {
        return sizeRepository
                .findById(id)
                .orElseThrow(() -> new ResponseException(NotFoundError.SIZE_NOT_FOUND));
    }

    @Override
    public Page<SizeResponse> filter(SizeParam param, Pageable pageable) {
        Specification<Size> sizeSpec = SizeSpecification.filterSpec(param);
        Page<Size> sizePage = sizeRepository.findAll(sizeSpec, pageable);
        return sizePage.map(size -> objectMapper.convertValue(size, SizeResponse.class));
    }


}
