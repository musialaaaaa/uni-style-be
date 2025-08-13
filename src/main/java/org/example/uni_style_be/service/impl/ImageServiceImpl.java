package org.example.uni_style_be.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.example.uni_style_be.entities.Image;
import org.example.uni_style_be.enums.InternalServerError;
import org.example.uni_style_be.enums.InvalidInputError;
import org.example.uni_style_be.enums.NotFoundError;
import org.example.uni_style_be.exception.ResponseException;
import org.example.uni_style_be.mapper.ImageMapper;
import org.example.uni_style_be.model.response.ImageResponse;
import org.example.uni_style_be.repositories.ImageRepository;
import org.example.uni_style_be.service.ImageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ImageServiceImpl implements ImageService {

    ImageRepository imageRepository;
    ImageMapper imageMapper;

    @Value("${file.upload.dir:uploads}")
    @NonFinal
    String uploadDir;

    private final List<String> allowedExtensions = List.of("jpg", "jpeg", "png", "gif", "bmp", "webp");

    @Override
    public List<ImageResponse> uploadMultipleImages(MultipartFile[] files) throws IOException {
        // Tạo thư mục nếu chưa tồn tại
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        List<Image> uploadedImages = new ArrayList<>();

        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                continue;
            }

            // Validate file
            validateFile(file);

            // Tạo tên file unique
            String originalFileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            String fileName = generateUniqueFileName(originalFileName);

            // Tạo thư mục theo ngày
            String dateFolder = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
            Path dateFolderPath = uploadPath.resolve(dateFolder);
            if (!Files.exists(dateFolderPath)) {
                Files.createDirectories(dateFolderPath);
            }

            // Đường dẫn file đầy đủ
            Path filePath = dateFolderPath.resolve(fileName);

            // Copy file
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Lưu thông tin vào database
            Image image = Image.builder()
                    .originalName(originalFileName)
                    .fileName(fileName)
                    .filePath(filePath.toString())
                    .contentType(file.getContentType())
                    .fileSize(file.getSize())
                    .build();

            uploadedImages.add(image);
        }

        List<Image> imageSaved = imageRepository.saveAll(uploadedImages);

        return imageMapper.toImageResponse(imageSaved);
    }

    @Override
    public Resource downloadImage(String fileName) throws IOException {
        Image image = imageRepository.findByFileName(fileName)
                .orElseThrow(() -> new ResponseException(NotFoundError.FILE_NOT_FOUND));

        Path filePath = Paths.get(image.getFilePath());

        if (!Files.exists(filePath)) {
            throw new ResponseException(InternalServerError.INTERNAL_SERVER_ERROR);
        }

        try {
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new ResponseException(InternalServerError.INTERNAL_SERVER_ERROR);
            }
        } catch (MalformedURLException ex) {
            throw new ResponseException(InternalServerError.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ImageResponse getImageInfo(String fileName) {
        Image image = imageRepository.findByFileName(fileName)
                .orElseThrow(() -> new ResponseException(NotFoundError.FILE_NOT_FOUND));
        return  imageMapper.toImageResponse(image);
    }

    @Override
    public Void deleteImage(String fileName) throws IOException {
        Image image = imageRepository.findByFileName(fileName)
                .orElseThrow(() -> new ResponseException(NotFoundError.FILE_NOT_FOUND));

        // Xóa file trên disk
        Path filePath = Paths.get(image.getFilePath());
        if (Files.exists(filePath)) {
            Files.delete(filePath);
        }

        // Xóa record trong database
        imageRepository.delete(image);

        return null;
    }

    private void validateFile(MultipartFile file) {
        // Kiểm tra kích thước file (max 10MB)
        if (file.getSize() > 10 * 1024 * 1024) {
            throw new ResponseException(InvalidInputError.FILE_TOO_LARGE);
        }

        // Kiểm tra extension
        String originalFileName = file.getOriginalFilename();
        if (originalFileName == null || originalFileName.isEmpty()) {
            throw new ResponseException(InvalidInputError.INVALID_FILE);
        }

        String extension = getFileExtension(originalFileName).toLowerCase();
        if (!allowedExtensions.contains(extension)) {
            throw new ResponseException(InvalidInputError.INVALID_FILE_TYPE, allowedExtensions);
        }

        // Kiểm tra content type
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new ResponseException(InvalidInputError.INVALID_FILE_TYPE, allowedExtensions);
        }
    }

    private String generateUniqueFileName(String originalFileName) {
        String extension = getFileExtension(originalFileName);
        String baseName = getFileBaseName(originalFileName);
        return baseName + "_" + UUID.randomUUID().toString() + "." + extension;
    }

    private String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex == -1) {
            return "";
        }
        return fileName.substring(lastDotIndex + 1);
    }

    private String getFileBaseName(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex == -1) {
            return fileName;
        }
        return fileName.substring(0, lastDotIndex);
    }
}
