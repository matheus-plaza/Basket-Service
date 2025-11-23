package dev.java.ecommerce.basketservice.response;

import java.io.Serializable;
import java.math.BigDecimal;

public record PlatziProductReponse(Long id,
                                   String title,
                                   BigDecimal price) implements Serializable {
}
