package com.bankingBackend.bankingApi.services;

import com.bankingBackend.bankingApi.DTO.user.EmailRoleDTO;
import com.bankingBackend.bankingApi.exceptions.ResourceNotFoundException;
import com.bankingBackend.bankingApi.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class OtherService {
    @Autowired
    private UserRepository userRepository;

    public String getCurrentUserEmail(){
        OidcUser user = (OidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getEmail();
    }

    public String getCurrentDateAndTime(){
        return LocalDate.now() + " " + LocalTime.now();
    }

    public EmailRoleDTO getEmailAndRole(){
        String email = getCurrentUserEmail();
        EmailRoleDTO emailRoleDTO = new EmailRoleDTO();
        emailRoleDTO.setEmail(email);
        emailRoleDTO.setRole(userRepository.getRoleByEmail(email));

        return emailRoleDTO;
    }

    public File getLogs(){
        File file = new File("SomeWhere");
        if (file.exists()) {
            return file; // Return the File object
        } else {
            throw new ResourceNotFoundException("Log file does not exist !");
        }
    }
}
