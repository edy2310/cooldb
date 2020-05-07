package com.cooldb.core.services;

import com.cooldb.core.models.DatabaseUser;
import com.cooldb.core.repositories.DatabaseUserRepository;
import com.cooldb.security.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DatabaseUserService {

    @Autowired
    private DatabaseUserRepository databaseUserRepository;

    public DatabaseUser createDatabaseUser(String name){
        DatabaseUser databaseUser = new DatabaseUser();
        databaseUser.setUsername(name);
        databaseUser.setPassword(PasswordUtil.createPassword());
        databaseUser.setPermission(DatabaseUser.PERMISSION.RW);
        databaseUser =  databaseUserRepository.save(databaseUser);
        return databaseUser;
    }

    public DatabaseUser updateDatabaseUser(DatabaseUser databaseUser){
        Optional<DatabaseUser> optionalDatabaseUser = databaseUserRepository.findById(databaseUser.getId());
        if(optionalDatabaseUser.isPresent()){
            DatabaseUser databaseUserUpdated = optionalDatabaseUser.get();
            databaseUserUpdated.setPermission(databaseUser.getPermission());
            databaseUserUpdated.setPassword(databaseUser.getPassword());
            return databaseUserRepository.save(databaseUserUpdated);
        }else
            return null;

    }

    public boolean deleteDatabaseUser(Long id){
        Optional<DatabaseUser> optionalDatabaseUser = databaseUserRepository.findById(id);
        if (optionalDatabaseUser.isPresent()){
            databaseUserRepository.deleteById(id);
            return true;
        }else
            return false;
    }
}
