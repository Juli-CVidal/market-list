package com.market.list.controllers;

import com.market.list.entities.ApiResponse;
import com.market.list.entities.Product;
import com.market.list.exceptions.MarketException;
import com.market.list.handlers.ApiHandler;
import com.market.list.services.ProductService;
import com.market.list.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity<ApiResponse<Product>> createProduct(@RequestBody Product product) {
        try {
            productService.create(product);
            return apiHandler.handleSuccessCreation(product, Constants.PRODUCT_CREATED);
        } catch (MarketException me) {
            return apiHandler.handleExceptionMessage(product, Constants.PRODUCT_HAS_ERRORS(me.getMessage()));
        }
    }


    // ======== READ ========

    @GetMapping
    public ResponseEntity<ApiResponse<Product>> getProductById(@RequestParam(value = "id", required = false) String id) {
        try {
            if (isInvalidParam(id)) {
                return apiHandler.handleBadRequest(Constants.NO_PARAMS);
            }

            return apiHandler.handleSuccessGet(productService.findById(id), Constants.PRODUCT_FOUND);
        } catch (MarketException me) {
            return apiHandler.handleNotFound(me.getMessage());
        }
    }


    // ======== UPDATE ========

    @PutMapping
    public ResponseEntity<ApiResponse<Product>> updateProduct(@RequestBody Product product) {
        try {
            productService.update(product);
            return apiHandler.handleSuccessModification(product, Constants.PRODUCT_MODIFIED);
        } catch (MarketException me) {
            return apiHandler.handleExceptionMessage(product, Constants.PRODUCT_HAS_ERRORS(me.getMessage()));
        }
    }


    // ======== DELETE ========

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<Product>> deleteProduct(@RequestParam(value = "id", required = false) String id) {
        try {
            if (isInvalidParam(id)) {
                return apiHandler.handleBadRequest(Constants.NO_PARAMS);
            }

            productService.delete(id);
            return apiHandler.handleSuccessDeletion(Constants.PRODUCT_DELETED);
        } catch (MarketException me) {
            return apiHandler.handleExceptionMessage(null, me.getMessage());
        }
    }


    // ======== PARAMS VALIDATORS ========

    private boolean isInvalidParam(String param) {
        return null == param || param.isBlank();
    }
}
