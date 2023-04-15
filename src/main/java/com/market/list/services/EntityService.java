package com.market.list.services;

import com.market.list.exception.MarketException;
import com.market.list.utils.Constants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public abstract class EntityService<T,ID>{
    protected JpaRepository<T,ID> repository;

    public EntityService(JpaRepository<T, ID> repository) {
        this.repository = repository;
    }


    // ======== CREATE ========

    @Transactional
    public T create(T entity){
        return repository.save(entity);
    }


    // ======== READ ========

    @Transactional(readOnly = true)
    public T findById(ID id) throws MarketException {
        return repository.findById(id).orElseThrow(() -> new MarketException(Constants.NOT_FOUND));
    }


    // ======== UPDATE ========

    @Transactional
    public T update(T entity){
        return repository.save(entity);
    }


    // ======== DELETE ========

    @Transactional
    public void delete(ID id){
        repository.deleteById(id);
    }
}
