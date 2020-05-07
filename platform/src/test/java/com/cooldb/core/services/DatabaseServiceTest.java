package com.cooldb.core.services;

import com.cooldb.core.models.Database;
import com.cooldb.core.models.DatabaseUser;
import com.cooldb.core.repositories.DatabaseRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class DatabaseServiceTest {

    @Autowired
    private DatabaseService databaseService;

    @Autowired
    private DatabaseUserService databaseUserService;

    @Autowired
    private DatabaseRepository databaseRepository;

    @Test
    @Transactional
    public void testCreateDatabase(){
        String username = "MyCoolUsername";
        DatabaseUser databaseUser = databaseUserService.createDatabaseUser(username);

        Database database = databaseService.createDatabase(databaseUser, 0);

        assertNotNull(database.getId());
        assertEquals(username, database.getDatabaseUsers().get(0).getUsername());
        assertEquals(Database.PLAN.FREE.getValue(), database.getPlan().getValue());
    }

    @Test
    @Transactional
    public void testUpdateDatabasePlan(){
        // Create database
        String username = "MyCoolUsername";
        DatabaseUser databaseUser = databaseUserService.createDatabaseUser(username);
        Database database = databaseService.createDatabase(databaseUser, 0);
        assertEquals(Database.PLAN.FREE.getValue(), database.getPlan().getValue());

        // Update database plan
        database = databaseService.updateDatabasePlan(database.getId(), 1);
        assertNotNull(database.getId());
        assertEquals(Database.PLAN.PREMIUM.getValue(), database.getPlan().getValue());
    }

    @Test
    @Transactional
    public void testDeleteDatabase(){
        // Create database
        String username = "MyCoolUsername";
        DatabaseUser databaseUser = databaseUserService.createDatabaseUser(username);
        Database database = databaseService.createDatabase(databaseUser, 0);
        assertNotNull(database.getId());

        // Delete database
        Long id = database.getId();
        assertTrue(databaseService.deleteDatabase(id));
        assertFalse(databaseRepository.findById(id).isPresent());
    }
}
