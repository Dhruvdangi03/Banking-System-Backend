package com.bankingBackend.bankingApi.utils;

import com.auth0.jwk.*;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.*;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.bankingBackend.bankingApi.DTO.user.LoginDTO;
import com.bankingBackend.bankingApi.DTO.user.UserDTO;
import com.bankingBackend.bankingApi.enums.UserType;
import com.bankingBackend.bankingApi.repos.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.bankingBackend.bankingApi.enums.UserType.*;

@Component
public class JwtUtil {
    @Value("${okta.oauth2.issuer}")
    private String ISSUER;

    @Value("${okta.oauth2.client-id}")
    private String clientId;

    @Value("${okta.oauth2.client-secret}")
    private String clientSecret;

    @Value("${okta.oauth2.audience}")
    private String audience;

    @Autowired
    UserRepository userRepository;

    @Transactional
    public String generateToken(LoginDTO loginDTO, boolean isManagementApiBeingUsed) {
        String url = ISSUER + "oauth/token";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> responseEntity = getMapHttpEntity(loginDTO, isManagementApiBeingUsed, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(url, responseEntity, Map.class);

        return (String) Objects.requireNonNull(response.getBody()).get(isManagementApiBeingUsed ? "access_token" : "id_token");
    }

    private HttpEntity<Map<String, Object>> getMapHttpEntity(LoginDTO loginDTO, boolean isManagementApiBeingUsed, HttpHeaders headers) {
        Map<String, Object> body = new HashMap<>();
        body.put("client_id", clientId);
        body.put("client_secret", clientSecret);
        body.put("audience", isManagementApiBeingUsed ? ISSUER + "api/v2/" : audience);
        body.put("grant_type", isManagementApiBeingUsed ? "client_credentials" : "password");

        if(!isManagementApiBeingUsed){
            String firstName = userRepository.getUserByEmail(loginDTO.getEmail()).getFirstName();
            body.put("scope", " openid profile email ");
            body.put("username", firstName);
            body.put("email", loginDTO.getEmail());
            body.put("password", loginDTO.getPassword());
        }

        return new HttpEntity<>(body, headers);
    }

    private DecodedJWT getDecodedJWT(String token) {
        try {
            JwkProvider jwkProvider = new UrlJwkProvider(ISSUER);
            Jwk jwk = jwkProvider.get(JWT.decode(token).getKeyId());
            Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) jwk.getPublicKey());
            return JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(token);
        } catch (TokenExpiredException e) {
            throw new TokenExpiredException(e.getLocalizedMessage(), e.getExpiredOn());
        } catch (MissingClaimException e) {
            throw new MissingClaimException(e.getMessage());
        } catch (IncorrectClaimException e) {
            throw new IncorrectClaimException(e.getMessage(), e.getClaimName(), e.getClaimValue());
        } catch (JWTVerificationException e) {
            throw new JWTVerificationException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public String extractUserEmail(String token){
        return castToString(getDecodedJWT(token).getClaims().get("email"));
    }

    public UserDTO extractUserInfo(String idToken) {
        Map<String, Claim> userInfo = getDecodedJWT(idToken).getClaims();
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName(castToString(userInfo.get("given_name")));
        userDTO.setLastName(castToString(userInfo.get("family_name")));
        userDTO.setEmail(castToString(userInfo.get("email")));
        userDTO.setPassword(castToString(userInfo.get("name")).replaceAll(" ", ""));
        userDTO.setRole(CUSTOMER.getValue());
        return userDTO;
    }

    private String castToString(Claim claim){
        String str = String.valueOf(claim);
        return str.substring(1, str.length() -1);
    }
}
