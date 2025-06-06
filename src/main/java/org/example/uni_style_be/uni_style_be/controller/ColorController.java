package org.example.uni_style_be.uni_style_be.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.uni_style_be.uni_style_be.entities.Color;
import org.example.uni_style_be.uni_style_be.model.filter.ColorParam;
import org.example.uni_style_be.uni_style_be.model.request.ColorRequest;
import org.example.uni_style_be.uni_style_be.model.response.ColorResponse;
import org.example.uni_style_be.uni_style_be.service.ColorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/colors")
@RequiredArgsConstructor
@Tag(name = "Api màu")
public class ColorController {
  private final ColorService colorService;

  @GetMapping
  public Page<Color> filter(ColorParam colorParam, Pageable pageable) {
    return colorService.filter(colorParam, pageable);
  }

  @PostMapping
  public ColorResponse create(@Valid @RequestBody ColorRequest colorReq) {
    return colorService.create(colorReq);
  }

  @PutMapping("/{id}")
  public ColorResponse update(@PathVariable Long id, @Valid @RequestBody ColorRequest colorReq) {
    return colorService.update(id, colorReq);
  }

  @GetMapping("/{id}")
  public Color findById(@PathVariable Long id) {
    return colorService.findById(id);
  }

  @DeleteMapping("/{id}")
  public void deleteById(@PathVariable Long id) {
    colorService.delete(id);
  }
}
