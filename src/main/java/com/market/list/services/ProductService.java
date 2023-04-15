package com.market.list.services;


import com.market.list.entities.Product;
import com.market.list.exception.MarketException;
import com.market.list.handler.EntityHandler;
import com.market.list.handler.ValidatorHandlerImpl;
import com.market.list.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class ProductService extends EntityService<Product, Integer> {


    private final EntityHandler<Product> handler;

    @Autowired
    public ProductService(ProductRepository productRepository, ValidatorHandlerImpl<Product> handler) {
        super(productRepository);
        this.handler = handler;
    }


    // ======== CREATE ========
    @Override
    @Transactional
    public Product create(Product product) throws MarketException{
        handleProduct(product);
        return super.create(product);
    }


    // ======== READ ========

    @Override
    @Transactional(readOnly = true)
    public Product findById(Integer id) throws MarketException {
        return super.findById(id);
    }


    // ======== UPDATE ========


    @Transactional
    public Product update(Product product) throws MarketException{
        handleProduct(product);
        return super.update(product);
    }


    // ======== DELETE ========
    @Override
    @Transactional
    public void delete(Integer id) {
        super.delete(id);
    }


    // AUXILIARIES

    private void handleProduct(Product product) throws MarketException{
        handler.handle(product);
        product.setLastModification(new Date(System.currentTimeMillis()));
    }
}
