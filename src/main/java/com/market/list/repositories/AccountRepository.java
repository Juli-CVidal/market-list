package com.market.list.repositories;

import com.market.list.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

    @Transactional(readOnly = true)
    @Query("SELECT a FROM Account  a WHERE a.id = :id")
    Optional<Account> findById(@Param("id") Integer id);

    @Transactional(readOnly = true)
    @Query("SELECT a FROM Account a WHERE a.email = :email")
    Optional<Account> findByEmail(@Param("email") String email);
}
