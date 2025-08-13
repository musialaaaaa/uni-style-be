package org.example.uni_style_be.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.uni_style_be.model.response.ImageResponse;
import org.example.uni_style_be.model.response.ServiceResponse;
import org.example.uni_style_be.service.ImageService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
@Tag(name = "Ảnh")
@Slf4j
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/upload")
    @Operation(summary = "Tải ảnh lên")
    public ServiceResponse<List<ImageResponse>> uploadImages(
            @RequestParam("files") MultipartFile[] files) throws IOException {

        return ServiceResponse.ok(imageService.uploadMultipleImages(files));
    }

    @GetMapping("/download/{fileName}")
    @Operation(summary = "Tải ảnh xuống")
    public ResponseEntity<Resource> downloadImage(@PathVariable String fileName) throws IOException {

        Resource resource = imageService.downloadImage(fileName);
        ImageResponse imageInfo = imageService.getImageInfo(fileName);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(imageInfo.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + imageInfo.getOriginalName() + "\"")
                .body(resource);

    }

    @GetMapping("/view/{fileName}")
    @Operation(summary = "Xem ảnh")
    public ResponseEntity<Resource> viewImage(@PathVariable String fileName) throws IOException {

        Resource resource = imageService.downloadImage(fileName);
        ImageResponse imageInfo = imageService.getImageInfo(fileName);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(imageInfo.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline")
                .body(resource);

    }

    @DeleteMapping("/{fileName}")
    @Operation(summary = "Xoá ảnh")
    public ServiceResponse<Void> deleteImage(@PathVariable String fileName) throws IOException {
        return ServiceResponse.ok(imageService.deleteImage(fileName));
    }

    @GetMapping("/info/{fileName}")
    @Operation(summary = "Xem thông tin ảnh")
    public ServiceResponse<ImageResponse> getImageInfo(@PathVariable String fileName) {
        return ServiceResponse.ok(imageService.getImageInfo(fileName));
    }
}
