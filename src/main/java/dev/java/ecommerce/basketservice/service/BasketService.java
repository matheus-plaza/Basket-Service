package dev.java.ecommerce.basketservice.service;

import dev.java.ecommerce.basketservice.response.PlatziProductReponse;
import dev.java.ecommerce.basketservice.request.BasketRequest;
import dev.java.ecommerce.basketservice.entity.Basket;
import dev.java.ecommerce.basketservice.entity.Product;
import dev.java.ecommerce.basketservice.entity.Status;
import dev.java.ecommerce.basketservice.repository.BasketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BasketService {

    private final BasketRepository basketRepository;
    private final ProductService productService;

    public Optional<Basket> getBasketById(String id) {
        return basketRepository.findById(id);
    }

    public Basket createBasket(BasketRequest basketRequest) {
        basketRepository.findByClientAndStatus(basketRequest.getClientId(), Status.OPEN)
                .ifPresent(basket -> {
            throw new IllegalArgumentException("Basket already exists");
        });

        List<Product> products = new ArrayList<>();
        basketRequest.getProducts().forEach(product -> {
            PlatziProductReponse platziProductReponse = productService.getProduct(product.getId());
            products.add(Product.builder()
                .id(product.getId())
                .price(platziProductReponse.price())
                .title(platziProductReponse.title())
                .quantity(product.getQuantity())
                .build());
        });

        Basket basket = Basket.builder()
                .client(basketRequest.getClientId())
                .status(Status.OPEN)
                .products(products)
                .build();

        basket.calculateTotalPrice();
        return basketRepository.save(basket);
    }
}
