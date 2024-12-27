package com.bankingBackend.bankingApi.services;

import com.bankingBackend.bankingApi.DTO.user.UserInfoDTO;
import com.bankingBackend.bankingApi.entities.Account;
import com.bankingBackend.bankingApi.entities.User;
import com.bankingBackend.bankingApi.repos.AccountRepository;
import com.bankingBackend.bankingApi.repos.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.bankingBackend.bankingApi.enums.UserType.*;

@Service
public class UserService {
    @Autowired
    private OtherService service;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountRepository accountRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void updateUserPassword(String email, String password){
        User user = userRepository.getUserByEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setTimeOfUpdate(service.getCurrentDateAndTime());
        userRepository.save(user);
    }

    public User getUserByEmail(String email){
        return userRepository.getUserByEmail(email);
    }

    public String getUserRole(){
        return userRepository.getRoleByEmail(service.getCurrentUserEmail());
    }

    public UserInfoDTO userInfo(){
        return getUserInfo(getUserByEmail(service.getCurrentUserEmail()));
    }

    public List<UserInfoDTO> allUserInfo(){
        return userRepository.getAllUsers();
    }

    public UserInfoDTO getUserInfo(User user){
        return new UserInfoDTO(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getRole(),
                user.getAccountNumber()
        );
    }

    public UserInfoDTO userInfoByUserId(Long userId){
        return getUserInfo(userRepository.getUserById(userId));
    }

    @Transactional
    public UserInfoDTO updateUserById(Long userId, UserInfoDTO userInfoDTO){
        User user = updateUser(userRepository.getUserById(userId), userInfoDTO);
        return getUserInfo(user);
    }

    @Transactional
    public UserInfoDTO updateCurrentUser(UserInfoDTO userInfoDTO){
        User user = updateUser(userRepository.getUserByEmail(service.getCurrentUserEmail()), userInfoDTO);
        return getUserInfo(user);
    }

    @Transactional
    public User updateUser(User user, UserInfoDTO userInfoDTO){
        user.setFirstName(userInfoDTO.getFirstName());
        user.setLastName(userInfoDTO.getLastName());
        
        if(!user.getEmail().equals(userInfoDTO.getEmail())){
            changeEmailInAccount(user.getEmail(), userInfoDTO.getEmail());
            user.setEmail(userInfoDTO.getEmail());
        }
        
        user.setPhoneNumber(userInfoDTO.getPhoneNumber());
        user.setTimeOfUpdate(service.getCurrentDateAndTime());
        return userRepository.save(user);
    }

    public void deleteUserByUserId(Long userId) {
        User user = userRepository.getUserById(userId);
        user.setDeleted(true);
        userRepository.save(user);
    }

    public void makeAdmin(String email){
        User user = getUserByEmail(email);
        user.setRole(ADMIN.getValue());
        user.setTimeOfUpdate(service.getCurrentDateAndTime());

        userRepository.save(user);
    }

    public void makeEmployee(String email){
        User user = getUserByEmail(email);
        user.setRole(EMPLOYEE.getValue());
        user.setTimeOfUpdate(service.getCurrentDateAndTime());

        userRepository.save(user);
    }

    private void changeEmailInAccount(String current, String next) {
        Account account = accountRepository.getAccountByUserEmail(current);
        account.setUserEmail(next);
        account.setTimeOfUpdate(service.getCurrentDateAndTime());

        accountRepository.save(account);
    }
}
