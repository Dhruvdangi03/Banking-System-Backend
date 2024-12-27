package com.bankingBackend.bankingApi.services;

import com.bankingBackend.bankingApi.DTO.transaction.TransactionDTO;
import com.bankingBackend.bankingApi.DTO.transaction.TransferDTO;
import com.bankingBackend.bankingApi.entities.Account;
import com.bankingBackend.bankingApi.entities.Transaction;
import com.bankingBackend.bankingApi.repos.AccountRepository;
import com.bankingBackend.bankingApi.repos.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.bankingBackend.bankingApi.enums.TransactionType.*;

@Service
public class TransactionService {
    @Autowired
    OtherService service;
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    AccountRepository accountRepository;

    @Transactional
    public Long transfer(TransferDTO transferDTO) {
        Account currentAccount = getCurrentAccount();
        Account transferAccount = accountRepository.getAccountByAccountNumber(transferDTO.getAccountNumber());

        checkInsufficientBalance(currentAccount.getBalance(), transferDTO.getAmount());

        doTransaction(TRANSFER.getValue(), transferDTO.getAmount(), currentAccount.getAccountNumber(), transferAccount.getAccountNumber(), transferDTO.getDescription());
        updateBalanceInAccount(currentAccount, transferDTO.getAmount(), false);
        updateBalanceInAccount(transferAccount, transferDTO.getAmount(), true);
        return currentAccount.getBalance();
    }

    @Transactional
    void doTransaction(String type, Long amount, Long currentAccount, Long transferAccount, String description){
        Transaction transaction = new Transaction();
        transaction.setTransactionType(type);
        transaction.setAmount(amount);
        transaction.setAccountNumber(currentAccount);
        transaction.setRelatedAccountNumber(transferAccount);
        transaction.setDescription(description);
        transaction.setTimestamp(service.getCurrentDateAndTime());

        transactionRepository.save(transaction);
    }

    @Transactional
    public Long deposit(TransactionDTO transactionDTO){
        Account account = getCurrentAccount();
        doTransaction(DEPOSIT.getValue(), transactionDTO.getAmount(), account.getAccountNumber(), null, transactionDTO.getDescription());
        updateBalanceInAccount(account, transactionDTO.getAmount(), true);

        return account.getBalance();
    }

    @Transactional
    public Long withdrawal(TransactionDTO transactionDTO){
        Account account = getCurrentAccount();

        checkInsufficientBalance(account.getBalance(), transactionDTO.getAmount());

        doTransaction(WITHDRAWAL.getValue(), transactionDTO.getAmount(), account.getAccountNumber(), null, transactionDTO.getDescription());
        updateBalanceInAccount(account, transactionDTO.getAmount(), false);

        return account.getBalance();
    }

    public Transaction getTransactionById(Long transactionId){
        return transactionRepository.getTransactionById(transactionId);
    }
    private Account getCurrentAccount(){
        return accountRepository.getAccountByUserEmail(service.getCurrentUserEmail());
    }

    @Transactional
    private void updateBalanceInAccount(Account account, Long amount, boolean increment){
        Long updatedBalance =  increment ? account.getBalance() + amount : account.getBalance() - amount;
        account.setBalance(updatedBalance);
        account.setTimeOfUpdate(service.getCurrentDateAndTime());
        accountRepository.save(account);
    }

    private void checkInsufficientBalance(Long balance, Long amount){
        if(balance < amount){
            throw new RuntimeException("Insufficient Balance! Your Account Balance is => " + balance);
        }
    }
}
