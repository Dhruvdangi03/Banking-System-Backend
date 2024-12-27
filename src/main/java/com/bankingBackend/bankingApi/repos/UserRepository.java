package com.bankingBackend.bankingApi.repos;

import com.bankingBackend.bankingApi.DTO.user.UserInfoDTO;
import com.bankingBackend.bankingApi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT * FROM bank_user u WHERE u.email = :email AND u.is_deleted = false", nativeQuery = true)
    User getUserByEmail(@Param("email") String email);

    @Query(value = "SELECT u.role FROM bank_user u WHERE u.email = :email AND u.is_deleted = false", nativeQuery = true)
    String getRoleByEmail(@Param("email") String email);

    @Query(value = "SELECT * FROM bank_user u WHERE u.id = :userId AND u.is_deleted = false", nativeQuery = true)
    User getUserById(@Param("userId") Long userId);

    @Query("SELECT com.bankingBackend.bankingApi.DTO.user.UserInfo(u.firstName, u.lastName, u.email, u.phoneNumber, u.role, u.accountNumber)" +
            " FROM User u WHERE u.isDeleted = false")
    List<UserInfoDTO> getAllUsers();
}