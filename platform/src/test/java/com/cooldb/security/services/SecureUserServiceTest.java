package com.cooldb.security.services;

import com.cooldb.security.models.SecureUser;
import com.cooldb.utilities.TestUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SecureUserServiceTest {

    @Autowired
    private SecureUserService secureUserService;

    @Test
    @Transactional
    public void testCreateSecureUser(){
        SecureUser secureUser = TestUtil.generateSecureUser();
        secureUser = secureUserService.createSecureUser(secureUser);

        assertNotNull(secureUser);
        assertEquals(secureUser.getEmail(), TestUtil.generateSecureUser().getEmail());
    }

    @Test
    @Transactional
    public void testUpdateSecureUser(){
        //Create user
        SecureUser secureUser = TestUtil.generateSecureUser();
        secureUser = secureUserService.createSecureUser(secureUser);

        assertNotNull(secureUser);
        assertEquals(secureUser.getEmail(), TestUtil.generateSecureUser().getEmail());

        //Update user
        String newEmail = "newemail@email.com";
        String newPassword = "NewPassword123";
        secureUser.setEmail(newEmail);
        secureUser.setPassword(newPassword);
        secureUser = secureUserService.updateSecureUser(secureUser);

        assertNotNull(secureUser);
        assertEquals(secureUser.getEmail(), newEmail);
        assertEquals(secureUser.getPassword(), newPassword);
    }
}
