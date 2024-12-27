package com.bankingBackend.bankingApi.controllers;

import com.bankingBackend.bankingApi.DTO.transaction.TransactionDTO;
import com.bankingBackend.bankingApi.DTO.transaction.TransferDTO;
import com.bankingBackend.bankingApi.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
    @Autowired
    TransactionService transactionService;

    @PostMapping("/transfer")
    public ResponseEntity<?> transfer(TransferDTO transferDTO) {
        Long balance = transactionService.transfer(transferDTO);

        return ResponseEntity.ok("Your " + transferDTO.getAmount() + " is Transferred to " + transferDTO.getAccountNumber() + " ! \n" +
                                "Your current Balance is " + balance);
    }

    @PutMapping("/deposit")
    public ResponseEntity<?> deposit(TransactionDTO transactionDTO) {
        Long balance = transactionService.deposit(transactionDTO);

        return ResponseEntity.ok("Your " + transactionDTO.getAmount() + " is Successfully deposited ! \n" +
                                "Your current Balance is " + balance);
    }

    @PutMapping("/withdrawal")
    public ResponseEntity<?> withdrawal(TransactionDTO transactionDTO) {
        Long balance = transactionService.withdrawal(transactionDTO);

        return ResponseEntity.ok("Your " + transactionDTO.getAmount() + " is Successfully withdrawal ! \n" +
                "Your current Balance is " + balance);
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<?> viewTransaction(@PathVariable Long transactionId){
        return ResponseEntity.ok(transactionService.getTransactionById(transactionId));
    }
}
