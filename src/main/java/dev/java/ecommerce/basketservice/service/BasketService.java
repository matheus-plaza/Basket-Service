package dev.java.ecommerce.basketservice.service;

import dev.java.ecommerce.basketservice.request.PaymentRequest;
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
        basketRepository.findByClientAndStatus(basketRequest.clientId(), Status.OPEN)
                .ifPresent(basket -> {
            throw new IllegalArgumentException("Basket already exists");
        });

        List<Product> products = new ArrayList<>();
        basketRequest.products().forEach(product -> {
            PlatziProductReponse platziProductReponse = productService.getProduct(product.getId());
            products.add(Product.builder()
                .id(product.getId())
                .price(platziProductReponse.price())
                .title(platziProductReponse.title())
                .quantity(product.getQuantity())
                .build());
        });

        Basket basket = Basket.builder()
                .client(basketRequest.clientId())
                .status(Status.OPEN)
                .products(products)
                .build();

        basket.calculateTotalPrice();
        return basketRepository.save(basket);
    }

    public Basket updateBasket(String id, BasketRequest request) {
        Basket basket = getBasketById(id).orElseThrow(() -> new IllegalArgumentException("Basket not found"));

        List<Product> products = new ArrayList<>();
        request.products().forEach(product -> {
            PlatziProductReponse platziProductReponse = productService.getProduct(product.getId());
            products.add(Product.builder()
                    .id(platziProductReponse.id())
                    .title(platziProductReponse.title())
                    .price(platziProductReponse.price())
                    .quantity(product.getQuantity())
                    .build());
        });

        basket.setProducts(products);

        basket.calculateTotalPrice();
        return basketRepository.save(basket);
    }

    public Basket payBasket(String id, PaymentRequest request) {
        return getBasketById(id)
                .map(basket -> {
                    basket.setPaymentMethod(request.paymentMethod());
                    basket.setStatus(Status.SOLD);
                    return basketRepository.save(basket); // O map aceita esse return
                })
                .orElseThrow(() -> new IllegalArgumentException("Basket not found"));
    }
}
