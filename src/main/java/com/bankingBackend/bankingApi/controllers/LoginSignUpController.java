package com.bankingBackend.bankingApi.controllers;

import com.bankingBackend.bankingApi.DTO.user.LoginDTO;
import com.bankingBackend.bankingApi.DTO.user.UserDTO;
import com.bankingBackend.bankingApi.services.LoginSignUpService;
import com.bankingBackend.bankingApi.utils.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class LoginSignUpController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private LoginSignUpService loginSignUpService;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginDTO user) {
        boolean isAuthenticated = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())).isAuthenticated();

        boolean doesExist = loginSignUpService.doesUserExistInAuth0(jwtUtil.generateToken(user, true), user.getEmail());
        if(isAuthenticated && doesExist) {
            String jwt = jwtUtil.generateToken(user, false);
            return ResponseEntity.ok(jwt);
        }
        return new ResponseEntity<>("Incorrect Username or Password", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/sign_up")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDTO user) {
        String managementApiToken = jwtUtil.generateToken(new LoginDTO(user.getEmail(), user.getPassword()), true);
        loginSignUpService.createUser(user, managementApiToken);
        return ResponseEntity.ok(jwtUtil.generateToken(new LoginDTO(user.getEmail(), user.getPassword()), false));
    }

    @GetMapping
    public String homePage(OAuth2AuthenticationToken authentication) {
        OidcUser user = (OidcUser) authentication.getPrincipal();
        if(loginSignUpService.doesUserExistInDatabase(user.getEmail())){
            loginSignUpService.createUserInDatabase(user.getIdToken().getTokenValue());
        }

        return "WELCOME to the BankEnd of Banking Application " + user.getFullName() + " !";
    }
}
