package com.bankingBackend.bankingApi.controllers;

import com.bankingBackend.bankingApi.DTO.account.AccountDTO;
import com.bankingBackend.bankingApi.DTO.account.AccountUpdateDTO;
import com.bankingBackend.bankingApi.services.AccountService;
import com.bankingBackend.bankingApi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

import static com.bankingBackend.bankingApi.enums.UserType.*;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> createAccount(AccountDTO accountDTO) {
        return ResponseEntity.ok(accountService.createAccount(accountDTO));
    }

    @GetMapping
    public ResponseEntity<?> getAccount() {
        if(Objects.equals(userService.getUserRole(), CUSTOMER.getValue()))
            return ResponseEntity.ok(accountService.accountInfo());

        return ResponseEntity.ok(accountService.allAccountInfo());
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<?> getAccountByAccountNumber(@PathVariable Long accountNumber) {
        return ResponseEntity.ok(accountService.accountInfoByAccountNumber(accountNumber));
    }

    @GetMapping("/transactions")
    public ResponseEntity<?> getTransactions() {
        return ResponseEntity.ok(accountService.getTransactions());
    }

    @GetMapping("/{accountNumber}/transactions")
    public ResponseEntity<?> getTransactionsByAccountNumber(@PathVariable Long accountNumber) {
        return ResponseEntity.ok(accountService.getTransactionsByAccountNumber(accountNumber));
    }

    @PutMapping
    public ResponseEntity<?> updateAccount(AccountUpdateDTO accountUpdateDTO) {
        return ResponseEntity.ok(accountService.updateAccountByUserEmail(accountUpdateDTO));
    }

    @PutMapping("/{accountNumber}")
    public ResponseEntity<?> updateAccountByAccountNumber(@PathVariable Long accountNumber, AccountUpdateDTO accountUpdateDTO) {
        return ResponseEntity.ok(accountService.updateAccountByAccountNumber(accountNumber, accountUpdateDTO));
    }

    @DeleteMapping("/{accountNumber}")
    public ResponseEntity<?> deleteAccount(@PathVariable Long accountNumber) {
        accountService.deleteAccount(accountNumber);
        return ResponseEntity.noContent().build();
    }
}
