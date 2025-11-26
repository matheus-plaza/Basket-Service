package dev.java.ecommerce.basketservice.request;

import dev.java.ecommerce.basketservice.entity.PaymentMethod;

public record PaymentRequest(
        PaymentMethod paymentMethod) {
}
