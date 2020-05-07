package com.cooldb.utilities;

import com.cooldb.security.models.SecureUser;
import com.cooldb.security.models.TokenUser;
import com.cooldb.security.util.PasswordUtil;
import com.cooldb.security.util.TokenUtil;

public class TestUtil {

    public static SecureUser generateSecureUser(){
        SecureUser secureUser = new SecureUser();
        secureUser.setEmail("user@mail.com");
        secureUser.setPassword(PasswordUtil.generatePassword("SuperPassword"));
        return secureUser;
    }

    public static TokenUser generateTokenUser(SecureUser secureUser){
        String token = TokenUtil.generateToken(secureUser.getEmail());

        TokenUser tokenUser = new TokenUser();
        tokenUser.setToken(token);
        tokenUser.setUser(secureUser);
        return tokenUser;
    }
}
