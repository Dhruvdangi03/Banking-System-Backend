package com.bankingBackend.bankingApi.services;

import com.bankingBackend.bankingApi.DTO.user.UserDTO;
import com.bankingBackend.bankingApi.entities.User;
import com.bankingBackend.bankingApi.repos.UserRepository;
import com.bankingBackend.bankingApi.utils.JwtUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Service
public class LoginSignUpService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtil jwtUtil;

    @Value("${okta.oauth2.issuer}")
    private String issuer;

    private final RestTemplate restTemplate = new RestTemplate();
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Transactional
    public void createUser(UserDTO userDTO, String managementApiToken){
        saveUserInAuth0(userDTO, managementApiToken);
        saveUserInDatabase(userDTO);
    }

    @Transactional
    public void saveUserInDatabase(UserDTO userDTO){
        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole(userDTO.getRole());
        user.setTimeOfCreation(LocalDate.now() + " " + LocalTime.now());
        user.setTimeOfUpdate(LocalDate.now() + " " + LocalTime.now());
        userRepository.save(user);
    }

    @Transactional
    public void saveUserInAuth0(UserDTO user, String managementApiToken){
        String url = issuer + "api/v2/users";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + managementApiToken);

        HttpEntity<Map<String, Object>> responseEntity = getMapHttpEntity(user, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, responseEntity, String.class);

        if (response.getStatusCode() != HttpStatus.CREATED) {
            throw new RuntimeException("Error creating user in Auth0: " + response.getBody());
        }
    }

    @Transactional
    private HttpEntity<Map<String, Object>> getMapHttpEntity(UserDTO user, HttpHeaders headers) {
        Map<String, Object> userPayload = new HashMap<>();
        userPayload.put("email", user.getEmail());
        userPayload.put("username", user.getFirstName());
        userPayload.put("name", user.getFirstName() + " " + user.getLastName());
        userPayload.put("password", user.getPassword()); // Or handle passwords securely
        userPayload.put("connection", "Username-Password-Authentication"); // Auth0 database connection
        userPayload.put("email_verified", false);

        return new HttpEntity<>(userPayload, headers);
    }

    public boolean doesUserExistInAuth0(String managementApiToken, String email) {
        String url = issuer + "api/v2/users-by-email?email=" + email;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + managementApiToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<ArrayList> response = restTemplate.exchange(url, HttpMethod.GET, entity, ArrayList.class);

        return response.getStatusCode() == HttpStatus.OK && !Objects.requireNonNull(response.getBody()).isEmpty();
    }

    @Transactional
    public void createUserInDatabase(String idToken) {
        saveUserInDatabase(jwtUtil.extractUserInfo(idToken));
    }

    public boolean doesUserExistInDatabase(String email) {
        return userRepository.getUserByEmail(email) == null;
    }
}
