package org.example.uni_style_be.uni_style_be.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.uni_style_be.uni_style_be.entities.Material;
import org.example.uni_style_be.uni_style_be.model.filter.MaterialParam;
import org.example.uni_style_be.uni_style_be.model.request.MaterialRequest;
import org.example.uni_style_be.uni_style_be.model.response.MaterialResponse;
import org.example.uni_style_be.uni_style_be.service.MaterialService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/materials")
@RequiredArgsConstructor
@Tag(name = "Api chất liệu")
public class MaterialController {

  private final MaterialService materialService;

  @GetMapping
  public Page<Material> filter(MaterialParam param, Pageable pageable) {
    return materialService.filter(param, pageable);
  }

  @PostMapping
  public MaterialResponse create(@Valid @RequestBody MaterialRequest req) {
    return materialService.create(req);
  }

  @PutMapping("/{id}")
  public MaterialResponse update(@PathVariable Long id, @Valid @RequestBody MaterialRequest req) {
    return materialService.update(id, req);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) {
    materialService.delete(id);
  }
}
