package com.market.list.controllers;

import com.market.list.entities.ApiResponse;
import com.market.list.entities.Listing;
import com.market.list.entities.Product;
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
            if (null == id){
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

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Listing>> deleteAccount(@PathVariable String id) {
        try {
            listingService.delete(id);
            return apiHandler.handleSuccessDeletion(Constants.LISTING_DELETED);
        } catch (MarketException me) {
            return apiHandler.handleExceptionMessage(null, me.getMessage());
        }
    }


    // ======== RELATED TO ADD OR DELETE A PRODUCT ========


    @PutMapping("/{listingId}/add/{productId}")
    public ResponseEntity<ApiResponse<Listing>> addProductToListing(@PathVariable("listingId") String listingId, @PathVariable("productId") String productId) {
        try {
            listingService.addProductToListing(listingId, productId);
            return apiHandler.handleSuccessAddition(Constants.PRODUCT_ADDED_TO_LIST);
        } catch (MarketException me) {
            return apiHandler.handleExceptionMessage(null, Constants.LISTING_HAS_ERRORS(me.getMessage()));
        }
    }


    @PutMapping("/{listingId}/remove/{productId}")
    public ResponseEntity<ApiResponse<Listing>> removeProductFromListing(@PathVariable("listingId") String listingId,@PathVariable("productId") String productId){
        try{
            listingService.removeProductFromListing(listingId,productId);
            return apiHandler.handleSuccessRemoving(Constants.PRODUCT_REMOVED_FROM_LIST);
        } catch(MarketException me){
            return apiHandler.handleExceptionMessage(null,Constants.LISTING_HAS_ERRORS(me.getMessage()));
        }
    }

}
