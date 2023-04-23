package com.market.list.controllers;

import com.market.list.entities.ApiResponse;
import com.market.list.entities.Listing;
import com.market.list.exception.MarketException;
import com.market.list.handler.ApiHandler;
import com.market.list.services.ListingService;
import com.market.list.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/listing")
public class ListingController {

    private final ListingService listingService;


    private final ApiHandler<Listing> apiHandler;

    @Autowired
    public ListingController(ListingService listingService, ApiHandler<Listing> apiHandler) {
        this.listingService = listingService;
        this.apiHandler = apiHandler;
    }


    // ======== CREATE ========

    @PostMapping
    public ResponseEntity<ApiResponse<Listing>> createListing(@RequestBody Listing listing) {
        listingService.create(listing);
        return apiHandler.handleSuccessCreation(listing, Constants.LISTING_CREATED);
    }


    // ======== READ ========

    @GetMapping
    public ResponseEntity<ApiResponse<Listing>> getListingById(@RequestParam(value = "id", required = false) String id) {
        try {
            if (isInvalidParam(id)) {
                return apiHandler.handleBadRequest(Constants.NO_PARAMS);
            }

            return apiHandler.handleSuccessGet(listingService.findById(id), Constants.LISTING_FOUND);
        } catch (MarketException me) {
            return apiHandler.handleNotFound(me.getMessage());
        }
    }


    // ======== UPDATE ========

    @PutMapping
    public ResponseEntity<ApiResponse<Listing>> updateListing(@RequestBody Listing listing) {
        try {
            listingService.update(listing);
            return apiHandler.handleSuccessModification(listing, Constants.LISTING_MODIFIED);
        } catch (MarketException me) {
            return apiHandler.handleExceptionMessage(listing, Constants.LISTING_HAS_ERRORS(me.getMessage()));
        }
    }


    // ======== DELETE ========

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<Listing>> deleteAccount(@RequestParam(value = "id", required = false) String id) {
        try {
            if (isInvalidParam(id)) {
                return apiHandler.handleBadRequest(Constants.NO_PARAMS);
            }

            listingService.delete(id);
            return apiHandler.handleSuccessDeletion(Constants.LISTING_DELETED);
        } catch (MarketException me) {
            return apiHandler.handleExceptionMessage(null, me.getMessage());
        }
    }


    // ======== RELATED TO ADD OR DELETE A PRODUCT ========

    @PutMapping("/addProduct")
    public ResponseEntity<ApiResponse<Listing>> addProductToListing(@RequestParam(value = "listingId", required = false) String listingId,
                                                                    @RequestParam(value = "productId", required = false) String productId) {
        try {
            if (isInvalidParam(listingId) || isInvalidParam(productId)) {
                return apiHandler.handleBadRequest(Constants.NO_PARAMS);
            }

            listingService.addProductToListing(listingId, productId);
            return apiHandler.handleSuccessAddition(Constants.PRODUCT_ADDED_TO_LIST);
        } catch (MarketException me) {
            return apiHandler.handleExceptionMessage(null, Constants.LISTING_HAS_ERRORS(me.getMessage()));
        }
    }


    @PutMapping("/removeProduct")
    public ResponseEntity<ApiResponse<Listing>> removeProductFromListing(@RequestParam(value = "listingId", required = false) String listingId,
                                                                         @RequestParam(value = "productId", required = false) String productId) {
        try {
            if (isInvalidParam(listingId) || isInvalidParam(productId)) {
                return apiHandler.handleBadRequest(Constants.NO_PARAMS);
            }

            listingService.removeProductFromListing(listingId, productId);
            return apiHandler.handleSuccessRemoving(Constants.PRODUCT_REMOVED_FROM_LIST);
        } catch (MarketException me) {
            return apiHandler.handleExceptionMessage(null, Constants.LISTING_HAS_ERRORS(me.getMessage()));
        }
    }


    // ======== PARAMS VALIDATORS ========

    private boolean isInvalidParam(String param) {
        return null == param || param.isBlank();
    }
}
