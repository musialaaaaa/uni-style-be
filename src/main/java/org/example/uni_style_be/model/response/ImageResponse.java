package org.example.uni_style_be.model.response;

import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ImageResponse extends BaseResponse {

    String originalName;

    String fileName;

    String filePath;

    String contentType;

    Long fileSize;
}
