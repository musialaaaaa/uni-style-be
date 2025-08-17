package org.example.uni_style_be.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class MaterialResponse extends BaseResponse {

    private String code;

    private String name;

    private Boolean isDeleted;
}
