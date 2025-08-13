package org.example.uni_style_be.mapper;

import org.example.uni_style_be.entities.Image;
import org.example.uni_style_be.model.response.ImageResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface ImageMapper {
    ImageResponse toImageResponse(Image image);

    List<ImageResponse> toImageResponse(List<Image> images);
}
