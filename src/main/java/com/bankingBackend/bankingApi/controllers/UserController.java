package com.bankingBackend.bankingApi.controllers;

import com.bankingBackend.bankingApi.DTO.user.LoginDTO;
import com.bankingBackend.bankingApi.DTO.user.UserInfoDTO;
import com.bankingBackend.bankingApi.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

import static com.bankingBackend.bankingApi.enums.UserType.*;


@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(@Valid @RequestBody LoginDTO loginDTO) {
        userService.updateUserPassword(loginDTO.getEmail(), loginDTO.getPassword());

        return ResponseEntity.ok("Your Password have been changed successfully !");
    }

    @GetMapping
    public ResponseEntity<?> getUser() {
        if(Objects.equals(userService.getUserRole(), CUSTOMER.getValue()))
            return ResponseEntity.ok(userService.userInfo());

        return ResponseEntity.ok(userService.allUserInfo());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.userInfoByUserId(userId));
    }

    @PutMapping
    public ResponseEntity<?> updateUser(UserInfoDTO userInfoDTO) {
        return ResponseEntity.ok(userService.updateCurrentUser(userInfoDTO));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUserById(@PathVariable Long userId, UserInfoDTO userInfoDTO) {
        return ResponseEntity.ok(userService.updateUserById(userId, userInfoDTO));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long userId) {
        userService.deleteUserByUserId(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/token")
    public String getToken(OAuth2AuthenticationToken authentication) {
        OidcUser user = (OidcUser) authentication.getPrincipal();

        return user.getIdToken().getTokenValue();
    }
}
