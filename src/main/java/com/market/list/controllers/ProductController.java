package com.market.list.controllers;

import com.market.list.entities.Product;
import com.market.list.exception.MarketException;
import com.market.list.responses.EntityResponse;
import com.market.list.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/product")
public class ProductController {

    private final ProductService productService;


    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;

    }
    // ======== CREATE ========

    @PostMapping()
    public ResponseEntity<EntityResponse<Product>> createProduct(@RequestBody Product product){
        try{
            productService.create(product);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new EntityResponse<>(product,"Product created successfully"));
        } catch(MarketException me){
            return ResponseEntity.badRequest()
                    .body(new EntityResponse<>(product, me.getMessage()));
        }
    }

    // ======== READ ========

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") Integer id){
        Optional<Product> productOpt = productService.findById(id);
        if (productOpt.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productOpt.get());
    }



}
