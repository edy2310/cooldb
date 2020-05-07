package com.cooldb.core.controllers;

import com.cooldb.core.models.DatabaseUser;
import com.cooldb.core.services.DatabaseUserService;
import com.cooldb.security.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/databaseUser")
public class DatabaseUserController {

    @Autowired
    TokenService tokenService;

    @Autowired
    private DatabaseUserService databaseUserService;

    @PutMapping("/updateDatabaseUser/{token}")
    public ResponseEntity updateDatabaseUser(@RequestBody DatabaseUser databaseUser, @PathVariable String token){
        if(!tokenService.isValidToken(token))
            return ResponseEntity.badRequest().body("Invalid token");

        try{
            DatabaseUser updatedDatabaseUser = databaseUserService.updateDatabaseUser(databaseUser);
            return ResponseEntity.ok(updatedDatabaseUser);
        }catch (Exception e){
            System.out.println(e);
            return ResponseEntity.badRequest().body("DatabaseUser not found");
        }
    }
}
