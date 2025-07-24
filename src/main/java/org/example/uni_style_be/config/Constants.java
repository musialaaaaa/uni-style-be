package org.example.uni_style_be.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Constants {
    public static final String SYSTEM = "SYSTEM";

    public static class Roles {
        public static final String PRODUCT = "PRODUCT";
        public static final String ORDER = "ORDER";
        public static final String PAYMENT = "PAYMENT";
        public static final String COLOR = "COLOR";
        public static final String BRAND = "BRAND";
        public static final String CATEGORY = "CATEGORY";
        public static final String COUPON = "COUPON";
        public static final String CUSTOMER = "CUSTOMER";
        public static final String MATERIAL = "MATERIAL";
        public static final String SIZE = "SIZE";
        public static final String ACCOUNT = "ACCOUNT";
        public static final String ROLE = "ROLE";
    }
}
