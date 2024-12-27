package com.bankingBackend.bankingApi.services;

import com.bankingBackend.bankingApi.DTO.account.AccountDTO;
import com.bankingBackend.bankingApi.DTO.account.AccountInfo;
import com.bankingBackend.bankingApi.DTO.account.AccountUpdateDTO;
import com.bankingBackend.bankingApi.DTO.transaction.TransactionInfoDTO;
import com.bankingBackend.bankingApi.entities.Account;
import com.bankingBackend.bankingApi.entities.User;
import com.bankingBackend.bankingApi.repos.AccountRepository;
import com.bankingBackend.bankingApi.repos.TransactionRepository;
import com.bankingBackend.bankingApi.repos.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    @Autowired
    private OtherService service;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    @Transactional
    public AccountInfo createAccount(AccountDTO accountDTO) {
        Account account = new Account();
        account.setAccountNumber(System.currentTimeMillis());
        account.setAccountType(accountDTO.getAccountType());
        account.setBalance(accountDTO.getBalance());
        account.setTimeOfCreation(service.getCurrentDateAndTime());
        account.setTimeOfUpdate(account.getTimeOfCreation());
        account.setUserEmail(service.getCurrentUserEmail());

        setAccountNumberInUser(account.getAccountNumber(), account.getUserEmail());

        accountRepository.save(account);

        return getAccountInfo(account);
    }

    public AccountInfo accountInfo() {
        return getAccountInfo(accountRepository.getAccountByUserEmail(service.getCurrentUserEmail()));
    }

    public List<AccountInfo> allAccountInfo(){
        return accountRepository.getAllAccounts();
    }

    public AccountInfo accountInfoByAccountNumber(Long accountNumber){
        return getAccountInfo(accountRepository.getAccountByAccountNumber(accountNumber));
    }

    @Transactional
    public Account updateAccount(Account account, AccountUpdateDTO accountUpdateDTO){
        account.setAccountType(accountUpdateDTO.getAccountType());
        setAccountNumberInUser(null, account.getUserEmail());
        account.setUserEmail(accountUpdateDTO.getUserEmail());
        setAccountNumberInUser(account.getAccountNumber(), account.getUserEmail());
        account.setTimeOfUpdate(service.getCurrentDateAndTime());

        return accountRepository.save(account);
    }

    @Transactional
    public AccountInfo updateAccountByUserEmail(AccountUpdateDTO accountUpdateDTO){
        Account account = accountRepository.getAccountByUserEmail(service.getCurrentUserEmail());

        return getAccountInfo(updateAccount(account, accountUpdateDTO));
    }

    private AccountInfo getAccountInfo(Account account){
        return new AccountInfo(
                account.getBalance(),
                account.getAccountType(),
                account.getAccountNumber(),
                account.getUserEmail(),
                account.getTimeOfCreation()
        );
    }

    @Transactional
    public void deleteAccount(long accountNumber){
        Account account = accountRepository.getAccountByAccountNumber(accountNumber);
        account.setTimeOfUpdate(service.getCurrentDateAndTime());
        account.setDeleted(true);

        accountRepository.save(account);
    }

    @Transactional
    public AccountInfo updateAccountByAccountNumber(Long accountNumber, AccountUpdateDTO accountUpdateDTO){
        return getAccountInfo(updateAccount(accountRepository.getAccountByAccountNumber(accountNumber), accountUpdateDTO));
    }

    public void setAccountNumberInUser(Long accountNumber, String userEmail) {
        User user = userRepository.getUserByEmail(userEmail);
        user.setAccountNumber(accountNumber);
        user.setTimeOfUpdate(service.getCurrentDateAndTime());
        userRepository.save(user);
    }

    public List<TransactionInfoDTO> getTransactions(){
        return transactionRepository.getTransactionsByAccountNumber(getCurrentAccountNumber());
    }

    public List<TransactionInfoDTO> getTransactionsByAccountNumber(Long accountNumber){
        return transactionRepository.getTransactionsByAccountNumber(accountNumber);
    }

    private Long getCurrentAccountNumber(){
        return accountInfo().getAccountNumber();
    }
}
