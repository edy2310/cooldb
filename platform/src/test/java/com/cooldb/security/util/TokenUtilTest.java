package com.cooldb.security.util;

import com.cooldb.security.models.SecureUser;
import com.cooldb.security.models.TokenUser;
import com.cooldb.security.repositories.SecureUserRepository;
import com.cooldb.security.repositories.TokenUserRepository;
import com.cooldb.security.services.TokenService;
import com.cooldb.utilities.TestUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TokenUtilTest {

    @Autowired
    SecureUserRepository secureUserRepository;

    @Autowired
    TokenUserRepository tokenUserRepository;

    @Autowired
    TokenService tokenService;

    private static SecureUser secureUser;

    @BeforeAll
    private static void setUp(){
        secureUser = TestUtil.generateSecureUser();
    }

    @Test
    @Transactional
    public void testGenerateToken(){
        secureUser = secureUserRepository.save(secureUser);

        String token = TokenUtil.generateToken(secureUser.getEmail());
        TokenUser tokenUser = TestUtil.generateTokenUser(secureUser);
        tokenUser = tokenUserRepository.save(tokenUser);

        assertEquals(token, tokenUser.getToken());

        String wrongEmail = "123456";
        assertFalse(TokenUtil.generateToken(wrongEmail).equals(token));
    }

    @Test
    public void testVerifyToken(){
        String token = TokenUtil.generateToken(secureUser.getEmail());
        assertTrue(TokenUtil.verifyToken(token, secureUser.getEmail()));

        String wrongEmail = "123456";
        assertFalse(TokenUtil.verifyToken(token, wrongEmail));
    }

    @Test
    @Transactional
    public void testIsValidToken(){
        secureUser = secureUserRepository.save(secureUser);

        TokenUser tokenUser = TestUtil.generateTokenUser(secureUser);
        tokenUser = tokenUserRepository.save(tokenUser);

        assertTrue(tokenService.isValidToken(tokenUser.getToken()));

        String wrongEmail = "123456";
        String wronkToken = TokenUtil.generateToken(wrongEmail);
        assertFalse(tokenService.isValidToken(wronkToken));
    }
}
