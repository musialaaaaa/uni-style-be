package org.example.uni_style_be.model.filter;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.uni_style_be.enums.OrderStatus;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderParam {
    Long code;

    String phoneNumber;

    OrderStatus status;
}
