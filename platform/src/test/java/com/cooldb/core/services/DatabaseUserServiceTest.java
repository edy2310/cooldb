package com.cooldb.core.services;

import com.cooldb.core.models.DatabaseUser;
import com.cooldb.core.repositories.DatabaseUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class DatabaseUserServiceTest {

    @Autowired
    private DatabaseUserService databaseUserService;

    @Autowired
    private DatabaseUserRepository databaseUserRepository;

    @Test
    @Transactional
    public void testCreateDatabaseUser(){
        String username = "MyCoolUsername";
        DatabaseUser databaseUser = databaseUserService.createDatabaseUser(username);

        assertNotNull(databaseUser.getId());
        assertEquals(databaseUser.getUsername(), username);
    }

    @Test
    @Transactional
    public void testUpdateDatabaseUser(){
        // Create Database user
        String username = "MyCoolUsername";
        DatabaseUser databaseUser = databaseUserService.createDatabaseUser(username);

        assertNotNull(databaseUser.getId());

        //Update Database user
        String newPassword = "MyCoolUsername";
        databaseUser.setPassword(newPassword);
        databaseUser = databaseUserService.updateDatabaseUser(databaseUser);

        assertNotNull(databaseUser.getId());
        assertEquals(newPassword, databaseUser.getPassword());
    }

    @Test
    @Transactional
    public void testDeleteDatabaseUser(){
        // Create Database user
        String username = "MyCoolUsername";
        DatabaseUser databaseUser = databaseUserService.createDatabaseUser(username);
        assertNotNull(databaseUser.getId());

        // Delete Database user
        Long id = databaseUser.getId();
        assertTrue(databaseUserService.deleteDatabaseUser(id));
        assertFalse(databaseUserRepository.findById(id).isPresent());
    }
}
