package com.cooldb.security.services;

import com.cooldb.security.models.TokenUser;
import com.cooldb.security.repositories.TokenUserRepository;
import com.cooldb.security.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TokenService {

    @Autowired
    private TokenUserRepository tokenUserRepository;

    public boolean isValidToken(String token){
        try{
            Optional<TokenUser> optional = tokenUserRepository.findByToken(token);
            if(optional.isPresent() && TokenUtil.verifyToken(token, optional.get().getUser().getEmail()))
                return true;
            else
                return false;
        }catch (Exception e){
            System.out.println(e);
            return false;
        }

    }
}
