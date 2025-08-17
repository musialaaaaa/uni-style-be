package org.example.uni_style_be.service;

import org.example.uni_style_be.model.response.ImageResponse;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImageService {
    List<ImageResponse> uploadMultipleImages(MultipartFile[] files) throws IOException;

    Resource downloadImage(String fileName) throws IOException;

    ImageResponse getImageInfo(String fileName);

    Void deleteImage(String fileName) throws IOException;
}
