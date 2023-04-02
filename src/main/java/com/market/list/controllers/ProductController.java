package com.market.list.controllers;

import com.market.list.entities.ApiResponse;
import com.market.list.entities.Product;
import com.market.list.exception.MarketException;
import com.market.list.handler.ApiHandler;
import com.market.list.services.ProductService;
import com.market.list.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/product")
public class ProductController {

    private final ProductService productService;

    private final ApiHandler<Product> apiHandler;

    @Autowired
    public ProductController(ProductService productService, ApiHandler<Product> apiHandler) {
        this.productService = productService;
        this.apiHandler = apiHandler;

    }


    // ======== CREATE ========

    @PostMapping()
    public ResponseEntity<ApiResponse<Product>> createProduct(@RequestBody Product product) {
        try {
            productService.create(product);
            return apiHandler.handleSuccessCreation(product, Constants.PRODUCT_CREATED);
        } catch (MarketException me) {
            return apiHandler.handleExceptionMessage(null, Constants.PRODUCT_HAS_ERRORS(me.getMessage()));
        }
    }


    // ======== READ ========

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Product>> getProductById(@PathVariable("id") Integer id) {
        Optional<Product> productOpt = productService.findById(id);
        if (productOpt.isEmpty()) {
            return apiHandler.handleNotFound(Constants.PRODUCT_NOT_FOUND);

        }
        return apiHandler.handleSuccessGet(productOpt.get(), Constants.PRODUCT_FOUND);

    }


    // ======== UPDATE ========

    @PutMapping("/")
    public ResponseEntity<ApiResponse<Product>> updateProduct(@RequestBody Product product) {
        try {
            productService.update(product);
            return apiHandler.handleSuccessModification(product, Constants.PRODUCT_MODIFIED);
        } catch (MarketException me) {
            return apiHandler.handleExceptionMessage(product, Constants.PRODUCT_HAS_ERRORS(me.getMessage()));
        }
    }


    // ======== DELETE ========

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Product>> deleteProduct(@PathVariable Integer id) {
        try {
            productService.delete(id);
            return apiHandler.handleSuccessDeletion(Constants.PRODUCT_DELETED);

        } catch (MarketException me) {
            return apiHandler.handleExceptionMessage(null, Constants.PRODUCT_DELETED);
        }
    }
}
