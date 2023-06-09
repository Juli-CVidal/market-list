package com.market.list.services;

import com.market.list.entities.Group;
import com.market.list.entities.Listing;
import com.market.list.entities.Product;
import com.market.list.exceptions.MarketException;
import com.market.list.handlers.EntityHandler;
import com.market.list.handlers.ValidatorHandlerImpl;
import com.market.list.repositories.ListingRepository;
import com.market.list.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ListingService {

    private final ListingRepository listingRepository;

    private final EntityHandler<Listing> handler;

    private final ProductService productService;

    @Autowired
    public ListingService(ListingRepository listingRepository, ProductService productService, ValidatorHandlerImpl<Listing> handler) {
        this.listingRepository = listingRepository;
        this.productService = productService;
        this.handler = handler;
    }


    // ======== CREATE ========
    @Transactional
    public Listing create(Listing listing) throws MarketException {
        handler.handle(listing);
        return listingRepository.save(listing);
    }

    // ======== READ ========


    @Transactional(readOnly = true)
    public Listing findById(String id) throws MarketException {
        return listingRepository.findById(id).orElseThrow(() -> new MarketException(Constants.NOT_FOUND));
    }


    // ======== UPDATE ========


    @Transactional
    public void update(Listing listing) throws MarketException {
        handler.handle(listing);
        listingRepository.save(listing);
    }


    // ======== DELETE ========

    @Transactional
    public void delete(String id) throws MarketException {
        if (null != findById(id)) {
            listingRepository.deleteById(id);
        }
    }


    // ======== RELATED TO PRODUCT MANAGEMENT ========

    @Transactional
    public void addProductToListing(String listingId, String productId) throws MarketException {
        Listing listing = findById(listingId);
        Product product = productService.findById(productId);
        listing.getProducts().add(product);
        update(listing);
    }


    @Transactional
    public void removeProductFromListing(String listingId, String productId) throws MarketException {
        Listing listing = findById(listingId);
        Product product = productService.findById(productId);

        //if the product was in the list, then gets removed from the listing and can be deleted from the Product table
        //otherwise, is unnecessary to alter the listing
        if (listing.getProducts().remove(product)) {
            productService.delete(productId);
            update(listing);
        }
    }

    // ======== RELATED TO GROUP MANAGEMENT ========

    @Transactional
    public void setGroupToListing(Listing listing, Group group) throws MarketException {
        listing.setGroup(group);
        this.update(listing);
    }
}
