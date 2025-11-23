package dev.java.ecommerce.basketservice.service;

import dev.java.ecommerce.basketservice.client.PlatziStoreClient;
import dev.java.ecommerce.basketservice.response.PlatziProductReponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final PlatziStoreClient storeClient;

    @Cacheable(value = "products")
    public List<PlatziProductReponse> getAllProducts(){
        log.info("Getting all products");
        return storeClient.getAllProducts();
    }

    @Cacheable(value = "product", key = "#productId")
    public PlatziProductReponse getProduct(Long productId){
        log.info("Getting product with id {}", productId);
        return storeClient.getProduct(productId);
    }
}
