package dev.java.ecommerce.basketservice.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;


public record BasketRequest(
        @NotEmpty(message = "Id do cliente é obrigatório.")
        Long clientId,

        @NotNull
        List<ProductRequest> products) {
}
