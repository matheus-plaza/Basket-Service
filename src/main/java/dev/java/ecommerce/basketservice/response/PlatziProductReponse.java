package dev.java.ecommerce.basketservice.response;

import java.math.BigDecimal;

public record PlatziProductReponse(Long id,
                                   String title,
                                   BigDecimal price) {
}
