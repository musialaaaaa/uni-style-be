package org.example.uni_style_be.uni_style_be.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.uni_style_be.uni_style_be.entities.Size;
import org.example.uni_style_be.uni_style_be.model.filter.SizeParam;
import org.example.uni_style_be.uni_style_be.model.request.SizeRequest;
import org.example.uni_style_be.uni_style_be.model.response.SizeResponse;
import org.example.uni_style_be.uni_style_be.service.SizeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/size")
@RequiredArgsConstructor
@Tag(name = "Api kích thước")
public class SizeController {

  private final SizeService sizeService;

  @GetMapping
  public Page<Size> filter(SizeParam param, Pageable pageable) {
    return sizeService.filter(param, pageable);
  }

  @PostMapping
  public SizeResponse create(@Valid @RequestBody SizeRequest sizeReq) {
    return sizeService.create(sizeReq);
  }

  @PutMapping("/{id}")
  public SizeResponse update(@PathVariable Long id, @Valid @RequestBody SizeRequest sizeReq) {
    return sizeService.update(id, sizeReq);
  }

  @GetMapping("/{id}")
  public Size findByID(@PathVariable Long id) {
    return sizeService.findByID(id);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) {
    sizeService.delete(id);
  }
}
