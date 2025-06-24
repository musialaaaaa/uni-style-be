package org.example.uni_style_be.model.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.uni_style_be.enums.Gender;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponse extends BaseResponse {

    private Long id;

    private String code;

    private String fullName;

    private String email;

    private String phoneNumber;

    private Gender gender;

    private String city;

    private Boolean isDeleted;

}
