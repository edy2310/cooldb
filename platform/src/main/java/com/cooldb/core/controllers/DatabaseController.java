package com.cooldb.core.controllers;

import com.cooldb.core.models.Database;
import com.cooldb.core.models.DatabaseUser;
import com.cooldb.core.services.DatabaseService;
import com.cooldb.core.services.DatabaseUserService;
import com.cooldb.security.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/database")
public class DatabaseController {

    @Autowired
    TokenService tokenService;

    @Autowired
    private DatabaseUserService databaseUserService;

    @Autowired
    private DatabaseService databaseService;

    @PostMapping("/createDatabase/{name}/{planId}/{token}")
    public ResponseEntity createDatabase(@PathVariable String name, @PathVariable int planId, @PathVariable String token){
        if(!tokenService.isValidToken(token))
            return ResponseEntity.badRequest().body("Invalid token");

        DatabaseUser databaseUser = databaseUserService.createDatabaseUser(name);
        try{
            Database database = databaseService.createDatabase(databaseUser, planId);
            return ResponseEntity.ok(database);
        }catch (Exception e){
            System.out.println(e);
            return ResponseEntity.badRequest().body("Server error");
        }
    }

    @PutMapping("/updateDatabasePlan/{id}/{planId}/{token}")
    public ResponseEntity updateDatabasePlan(@PathVariable Long id, @PathVariable int planId, @PathVariable String token){
        if(!tokenService.isValidToken(token))
            return ResponseEntity.badRequest().body("Invalid token");

        try{
            Database database = databaseService.updateDatabasePlan(id, planId);
            return ResponseEntity.ok(database);
        }catch (Exception e){
            System.out.println(e);
            return ResponseEntity.badRequest().body("Database not found");
        }
    }

    @DeleteMapping("/deleteDatabase/{id}/{token}")
    public ResponseEntity deleteDatabase(@PathVariable Long id, @PathVariable String token){
        if(!tokenService.isValidToken(token))
            return ResponseEntity.badRequest().body("Invalid token");

        if(databaseService.deleteDatabase(id))
            return ResponseEntity.ok().build();
        else
            return ResponseEntity.badRequest().body("Database not found");
    }
}
