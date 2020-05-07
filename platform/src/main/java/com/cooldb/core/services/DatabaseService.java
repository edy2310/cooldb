package com.cooldb.core.services;

import com.cooldb.core.models.Database;
import com.cooldb.core.models.DatabaseUser;
import com.cooldb.core.repositories.DatabaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DatabaseService {

    @Autowired
    private DatabaseRepository databaseRepository;

    @Autowired
    private DatabaseUserService databaseUserService;

    public Database createDatabase(DatabaseUser databaseUser, int planId){
        List<DatabaseUser> databaseUserList = new ArrayList<>();
        databaseUserList.add(databaseUser);

        Database database = new Database();
        database.setName(databaseUser.getUsername());
        // Needs to change once the server is done
        database.setHost("example.host.com");
        // End
        database.setData(0.0);
        database.setPlan(Database.PLAN.fromValue(planId));
        database.setDatabaseUsers(databaseUserList);
        database = databaseRepository.save(database);

        databaseUser.setDatabase(database);
        return database;
    }

    public Database updateDatabasePlan(Long id, int planId){
        Optional<Database> optionalDatabase = databaseRepository.findById(id);
        if(optionalDatabase.isPresent()){
            optionalDatabase.get().setPlan(Database.PLAN.fromValue(planId));
            return databaseRepository.save(optionalDatabase.get());
        }
        else
            return null;
    }

    public boolean deleteDatabase(Long id){
        Optional<Database> optionalDatabase = databaseRepository.findById(id);
        if (optionalDatabase.isPresent()){
            for(DatabaseUser dbUser : optionalDatabase.get().getDatabaseUsers()){
                databaseUserService.deleteDatabaseUser(dbUser.getId());
            }
            databaseRepository.deleteById(id);
            return true;
        }else
            return false;
    }
}
