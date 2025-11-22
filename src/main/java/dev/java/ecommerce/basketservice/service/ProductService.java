package dev.java.ecommerce.basketservice.service;

import dev.java.ecommerce.basketservice.client.PlatziStoreClient;
import dev.java.ecommerce.basketservice.response.PlatziProductReponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final PlatziStoreClient storeClient;

    public List<PlatziProductReponse> getAllProducts(){
        return storeClient.getAllProducts();
    }

    public PlatziProductReponse getProduct(Long id){
        return storeClient.getProduct(id);
    }
}
