package com.manhnd.accountservice.dto;

import com.manhnd.accountservice.model.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDTO {
    private long id;
    private String email;
    private String currency;
    private double balance;
    private double reserved;

    public static AccountDTO entityToDto(Account account){
        return AccountDTO.builder()
                .email(account.getEmail())
                .currency(account.getCurrency())
                .balance(account.getBalance())
                .reserved(account.getReserved())
                .id(account.getId())
                .build();
    }

    public static Account dtoToEntity(AccountDTO accountDTO){
        Account account = new Account();
        account.setId(accountDTO.getId());
        account.setReserved(accountDTO.getReserved());
        account.setBalance(accountDTO.getBalance());
        account.setCurrency(accountDTO.getCurrency());
        account.setEmail(accountDTO.getEmail());
        return account;
    }
}
