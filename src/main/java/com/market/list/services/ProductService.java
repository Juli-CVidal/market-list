package com.market.list.services;


import com.market.list.entities.Product;
import com.market.list.handler.EntityHandler;
import com.market.list.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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
    public void create(Product product) {
       handleAndSave(product);
    }
    // ======== READ ========

    @Transactional(readOnly = true)
    public Optional<Product> findById(Integer id) {
        return productRepository.findById(id);
    }


    // ======== UPDATE ========

    @Transactional
    public void update(Product product){
      handleAndSave(product);
    }


    // ======== DELETE ========

    @Transactional
    public void delete(Integer id){
        productRepository.deleteById(id);
    }

    // AUXILIARIES

    private void handleAndSave(Product product){
        handlers.forEach(handler -> handler.handle(product));
        product.setLastModification(new Date(System.currentTimeMillis()));
        productRepository.save(product);
    }
}
