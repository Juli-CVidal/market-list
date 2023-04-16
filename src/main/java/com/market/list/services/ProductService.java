package com.market.list.services;


import com.market.list.exception.MarketException;
import com.market.list.handler.EntityHandler;
import com.market.list.handler.ValidatorHandlerImpl;
import com.market.list.entities.Product;
import com.market.list.repositories.ProductRepository;
import com.market.list.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    private final EntityHandler<Product> handler;

    @Autowired
    public ProductService(ProductRepository productRepository, ValidatorHandlerImpl<Product> handler) {
        this.productRepository = productRepository;
        this.handler = handler;
    }


    // ======== CREATE ========

    @Transactional
    public Product create(Product product) throws MarketException {
        handleProduct(product);
        return productRepository.save(product);
    }


    // ======== READ ========


    @Transactional(readOnly = true)
    public Product findById(Integer id) throws MarketException {
        return productRepository.findById(id).orElseThrow(() -> new MarketException(Constants.NOT_FOUND));
    }


    // ======== UPDATE ========


    @Transactional
    public Product update(Product product) throws MarketException {
        handleProduct(product);
        return productRepository.save(product);
    }


    // ======== DELETE ========

    @Transactional
    public void delete(Integer id) {
        productRepository.deleteById(id);
    }


    // AUXILIARIES

    private void handleProduct(Product product) throws MarketException {
        handler.handle(product);
        product.setLastModification(new Date(System.currentTimeMillis()));
    }
}
