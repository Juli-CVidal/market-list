package com.market.list.services;


import com.market.list.entities.Product;
import com.market.list.handler.EntityHandler;
import com.market.list.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {


    private final ProductRepository productRepository;

    private final List<EntityHandler<Product>> handlers;

    @Autowired
    public ProductService(ProductRepository productRepository, List<EntityHandler<Product>> handlers) {
        this.productRepository = productRepository;
        this.handlers = handlers;
    }

    // ======== CREATE ========
    @Transactional
    public Product create(Product product){
        handlers.forEach(handler -> {
            handler.handleObject(product);
        });

        return productRepository.save(product);
    }
    // ======== READ ========

    @Transactional
    public Optional<Product> findById(Integer id){
        return productRepository.findById(id);
    }
}
