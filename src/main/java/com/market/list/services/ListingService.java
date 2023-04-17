package com.market.list.services;

import com.market.list.entities.Listing;
import com.market.list.entities.Product;
import com.market.list.exception.MarketException;
import com.market.list.repositories.ListingRepository;
import com.market.list.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ListingService {

    private final ListingRepository listingRepository;

    @Autowired
    public ListingService(ListingRepository listingRepository) {
        this.listingRepository = listingRepository;
    }

    // ======== CREATE ========
    @Transactional
    public Listing create(Listing listing) {
        return listingRepository.save(listing);
    }

    // ======== READ ========


    @Transactional(readOnly = true)
    public Listing findById(Integer id) throws MarketException {
        return listingRepository.findById(id).orElseThrow(() -> new MarketException(Constants.NOT_FOUND));
    }


    // ======== UPDATE ========


    @Transactional
    public Listing update(Listing listing) throws MarketException {
        return listingRepository.save(listing);
    }


    // ======== DELETE ========

    @Transactional
    public void delete(Integer id) throws MarketException {
        if (null != findById(id)) {
            listingRepository.deleteById(id);
        }
    }

    // ======== OTHERS ========

    @Transactional
    public void addProductToListing(Integer listingId, Product product) throws MarketException {
        Listing listing = findById(listingId);
        listing.getProducts().add(product);
        update(listing);
    }
}
