package com.market.list.repositories;


import com.market.list.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE Product p SET p.quantity = :quantity WHERE p.id = :id")
        //This query will be used when a product has been purchased or used.
    void updateProductQuantity(@Param("id") Integer id, @Param("quantity") Integer quantity);
}
