package com.bankingBackend.bankingApi.repos;

import com.bankingBackend.bankingApi.DTO.account.AccountInfo;
import com.bankingBackend.bankingApi.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query(value = "SELECT * FROM account a WHERE a.user_email = :email AND a.is_deleted = false", nativeQuery = true)
    Account getAccountByUserEmail(@Param("email") String email);

    @Query(value = "SELECT * FROM account a WHERE a.account_number = :accountNumber AND a.is_deleted = false", nativeQuery = true)
    Account getAccountByAccountNumber(@Param("accountNumber") Long accountNumber);

    @Query("SELECT com.bankingBackend.bankingApi.DTO.account.AccountInfo(a.balance, a.accountType, a.accountNumber, a.userEmail, a.timeOfCreation)" +
            " FROM Account a WHERE a.isDeleted = false")
    List<AccountInfo> getAllAccounts();
}
