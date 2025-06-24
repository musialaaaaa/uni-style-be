package org.example.uni_style_be.controller;

import com.fasterxml.jackson.databind.JsonMappingException;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.uni_style_be.entities.Brand;
import org.example.uni_style_be.service.BrandService;
import org.example.uni_style_be.model.filter.BrandParam;
import org.example.uni_style_be.model.request.BrandRequest;
import org.example.uni_style_be.model.response.BrandReponse;
import org.example.uni_style_be.utils.PageUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/brands")
@RequiredArgsConstructor
@Tag(name = "Api thương hiệu")
public class BrandController {
  private final BrandService brandService;

  @GetMapping
  public PageUtils<BrandReponse> filter(BrandParam param, Pageable pageable) {
    return new PageUtils<>(brandService.filter(param,pageable));
  }

  @PostMapping
  public BrandReponse create(@Valid @RequestBody BrandRequest brandRequest) {
    return brandService.create(brandRequest);
  }

  @PutMapping("/{id}")
  public BrandReponse update(@PathVariable Long id, @Valid @RequestBody BrandRequest brandRequest) throws JsonMappingException {
    return brandService.update(id, brandRequest);
  }

  @GetMapping("/{id}")
  public Brand findById(@PathVariable Long id) {
    return brandService.findById(id);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) {
    brandService.delete(id);
  }
}
