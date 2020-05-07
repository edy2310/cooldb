package com.cooldb.security.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;

public class TokenUtil {

    private static final String code = "SoftKitty1012";

    public static String generateToken(String email){
        try {
            Algorithm algorithm = Algorithm.HMAC256(code);
            String token = JWT.create().withSubject(email).sign(algorithm);
            return token;
        } catch (JWTCreationException | UnsupportedEncodingException e){
            System.out.println(e);
        }
        return null;
    }

    public static boolean verifyToken(String token, String email){
        try {
            Algorithm algorithm = Algorithm.HMAC256(code);
            JWTVerifier verifier = JWT.require(algorithm).withSubject(email).build();
            DecodedJWT jwt = verifier.verify(token);

            if(jwt.getSubject().equals(email))
                return true;
            else
                return false;
        } catch (JWTVerificationException | UnsupportedEncodingException e){
            System.out.println(e);
        }
        return false;
    }
}
