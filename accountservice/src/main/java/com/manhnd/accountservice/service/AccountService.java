package com.manhnd.accountservice.service;

import com.manhnd.accountservice.dto.AccountDTO;
import com.manhnd.accountservice.repository.AccountRepository;
import com.manhnd.commonservice.commons.CommonException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    public Mono<AccountDTO> createNewAccount(AccountDTO accountDTO){
        return Mono.just(accountDTO)
                .map(AccountDTO::dtoToEntity)
                .flatMap(account -> accountRepository.save(account))
                .map(AccountDTO::entityToDto)
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }
    public Mono<AccountDTO> checkBalance(String id){
        return findById(id);
    }
    public Mono<AccountDTO> findById(String id){
        return accountRepository.findAccountById(id)
                .map(AccountDTO::entityToDto)
                .switchIfEmpty(Mono.error(new CommonException("A01", "Account not found", HttpStatus.NOT_FOUND)));
    }

    public Mono<AccountDTO> getDetailAccountByEmail(AccountDTO accountDTO) {
        return accountRepository.findDetailAccountByEmail(accountDTO.getEmail())
                .map(AccountDTO::entityToDto)
                .switchIfEmpty(Mono.error(new CommonException("01","Not Found", HttpStatus.NOT_FOUND)));
    }

    public Mono<AccountDTO> updateInitialBalance(AccountDTO accountDTO) {
        return getDetailAccountByEmail(accountDTO)
                .map(AccountDTO::dtoToEntity)
                .flatMap(account -> {
                    account.setBalance(accountDTO.getBalance());
                    return accountRepository.save(account);
                }).map(AccountDTO::entityToDto)
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }
}