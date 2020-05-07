package com.cooldb.security.controllers;

import com.cooldb.security.models.SecureUser;
import com.cooldb.security.models.TokenUser;
import com.cooldb.security.repositories.SecureUserRepository;
import com.cooldb.security.repositories.TokenUserRepository;
import com.cooldb.security.services.SecureUserService;
import com.cooldb.security.services.TokenService;
import com.cooldb.security.util.PasswordUtil;
import com.cooldb.security.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/security")
public class SecureUserController {

    @Autowired
    private SecureUserService secureUserService;

    @Autowired
    private SecureUserRepository secureUserRepository;

    @Autowired
    private TokenUserRepository tokenUserRepository;

    @Autowired
    TokenService tokenService;

    @PostMapping("/createUser")
    public ResponseEntity createUser(@RequestBody SecureUser secureUser){
        SecureUser newUser = secureUserService.createSecureUser(secureUser);
        if(!newUser.equals(null))
            return ResponseEntity.ok(newUser);
        else
            return ResponseEntity.badRequest().body("Email already registered");
    }

    @PutMapping("/updateUser/{token}")
    public ResponseEntity updateUser(@RequestBody SecureUser secureUser, @PathVariable String token){
        if(!tokenService.isValidToken(token))
            return ResponseEntity.badRequest().body("Invalid token");

        SecureUser updatedUser = secureUserService.updateSecureUser(secureUser);
        if(!updatedUser.equals(null))
            return ResponseEntity.ok(updatedUser);
        else
            return ResponseEntity.badRequest().body("Not user found");

    }

    @PostMapping("/getToken")
    public ResponseEntity getToken(@RequestBody SecureUser secureUser){
        Optional<TokenUser> optionalTokenUser= tokenUserRepository.findByUserId(secureUser.getId());
        if (optionalTokenUser.isPresent())
            return ResponseEntity.badRequest().body("Session already created");

        Optional<SecureUser> optionalSecureUser = secureUserRepository.findByEmail(secureUser.getEmail());

        if(optionalSecureUser.isPresent() &&
                PasswordUtil.verifyPassword(secureUser.getPassword(), PasswordUtil.generatePassword(secureUser.getPassword()))){

            String token = TokenUtil.generateToken(optionalSecureUser.get().getEmail());
            TokenUser tokenUser = new TokenUser();
            tokenUser.setToken(token);
            tokenUser.setUser(secureUser);
            tokenUserRepository.save(tokenUser);
            return ResponseEntity.ok(token);
        }
        else
            return ResponseEntity.badRequest().body("Invalid credentials");
    }

    @DeleteMapping("/closeSession/{id}/{token}")
    public ResponseEntity closeSession(@PathVariable Long id, @PathVariable String token){
        if(!tokenService.isValidToken(token)){
            return ResponseEntity.badRequest().body("Invalid token");
        }

        Optional<TokenUser> optionalTokenUser = tokenUserRepository.findByUserId(id);

        if(optionalTokenUser.isPresent()){
            tokenUserRepository.deleteById(optionalTokenUser.get().getId());
            return ResponseEntity.ok().build();
        }
        else
            return ResponseEntity.badRequest().body("Session not found");
    }
}
