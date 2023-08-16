package com.manhnd.accountservice.repository;

import com.manhnd.accountservice.model.Account;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;
import org.springframework.stereotype.Repository;
@Repository
public interface AccountRepository extends ReactiveCrudRepository<Account,String> {

    @Query(value = "SELECT * FROM ACCOUNTS WHERE ID = :id", nativeQuery = true)
    Mono<Account> findAccountById(String id);

    @Query(value = "SELECT * FROM ACCOUNTS WHERE EMAIL = :email", nativeQuery = true)
    Mono<Account> findDetailAccountByEmail(String email);
}
