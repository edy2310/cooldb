package com.cooldb.security.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    public static String createPassword(){
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*()-_=+[{]}\\|;:\'\",<.>/?";
        return RandomStringUtils.random( 15, characters );
    }

    public static String generatePassword(String password_plaintext) {
        return BCrypt.hashpw(password_plaintext, BCrypt.gensalt());
    }

    public static boolean verifyPassword(String password_plaintext, String hashed){
        if (BCrypt.checkpw(password_plaintext, hashed))
            return true;
        else
            return false;
    }
}
