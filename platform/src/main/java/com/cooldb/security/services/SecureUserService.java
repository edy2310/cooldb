package com.cooldb.security.services;

import com.cooldb.security.models.SecureUser;
import com.cooldb.security.repositories.SecureUserRepository;
import com.cooldb.security.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SecureUserService {

    @Autowired
    private SecureUserRepository secureUserRepository;

    public SecureUser createSecureUser(SecureUser secureUser){
        Optional<SecureUser> optionalSecureUser = secureUserRepository.findByEmail(secureUser.getEmail());

        if (optionalSecureUser.isPresent())
            return null;
        else{
            SecureUser newUser = new SecureUser();
            newUser.setEmail(secureUser.getEmail());
            newUser.setPassword(PasswordUtil.generatePassword(secureUser.getPassword()));
            newUser = secureUserRepository.save(newUser);
            return newUser;
        }
    }

    public SecureUser updateSecureUser(SecureUser secureUser){
        Optional<SecureUser> optionalSecureUser = secureUserRepository.findById(secureUser.getId());

        if (!optionalSecureUser.isPresent())
            return null;
        else{
            optionalSecureUser.get().setPassword(secureUser.getPassword());
            optionalSecureUser.get().setEmail(secureUser.getEmail());
            return secureUserRepository.save(optionalSecureUser.get());
        }
    }
}
