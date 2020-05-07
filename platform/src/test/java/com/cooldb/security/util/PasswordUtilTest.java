package com.cooldb.security.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PasswordUtilTest {

    @Test
    public void testCreatePassword(){
        String password = PasswordUtil.createPassword();
        assertTrue(password.length() == 15);
        assertNotEquals(password, PasswordUtil.createPassword());
    }

    @Test
    public void testGeneratePassword(){
        String passwordPlain = "Skuichi123456";
        String passwordEncrypted = PasswordUtil.generatePassword(passwordPlain);
        assertTrue(PasswordUtil.verifyPassword(passwordPlain, passwordEncrypted));

        String badPasswordPlain = "456";
        assertFalse(PasswordUtil.verifyPassword(badPasswordPlain, passwordEncrypted));
    }
}
