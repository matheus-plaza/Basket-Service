package dev.java.ecommerce.basketservice.client;

import dev.java.ecommerce.basketservice.response.PlatziProductReponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "PlatziStoreClient", url = "${basket.client.platzi}")
public interface PlatziStoreClient {

    @GetMapping("/products")
    List<PlatziProductReponse> getAllProducts();

    @GetMapping("/products/{id}")
    PlatziProductReponse getProduct(@PathVariable Long id);
}