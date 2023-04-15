package com.market.list.services;

import com.market.list.entities.Listing;
import com.market.list.entities.Product;
import com.market.list.exception.MarketException;
import com.market.list.repositories.ListingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ListingService extends EntityService<Listing,Integer>{

    @Autowired
    public ListingService(ListingRepository listingRepository) {
        super(listingRepository);
    }

    // ======== CREATE ========
    @Override
    @Transactional
    public Listing create(Listing listing) throws MarketException{
        return super.create(listing);
    }


    // ======== READ ========

    @Override
    @Transactional(readOnly = true)
    public Listing findById(Integer id) throws MarketException {
       return super.findById(id);
    }


    // ======== UPDATE ========

    @Override
    @Transactional
     public Listing update(Listing listing) throws MarketException{
        return super.update(listing);
     }


     // ======== OTHERS ========

    @Transactional
    public void addProductToListing(Integer listingId, Product product) throws MarketException{
        Listing listing = findById(listingId);
        listing.getProducts().add(product);
        super.update(listing);
    }
}
