package com.market.list.services;


import com.market.list.entities.Product;
import com.market.list.exceptions.MarketException;
import com.market.list.handlers.EntityHandler;
import com.market.list.handlers.ValidatorHandlerImpl;
import com.market.list.repositories.ProductRepository;
import com.market.list.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
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
    public Product findById(String id) throws MarketException {
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
    public void delete(String id) throws MarketException {
        if (null != findById(id)) {
            productRepository.deleteById(id);
        }
    }


    // AUXILIARIES

    private void handleProduct(Product product) throws MarketException {
        handler.handle(product);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy - HH:mm");
        product.setLastModification(formatter.format(new Date(System.currentTimeMillis())));
    }
}
