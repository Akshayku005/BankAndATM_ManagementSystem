package com.bridgelabz.Bank_Application.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.auth0.jwt.interfaces.Verification;
import org.springframework.stereotype.Component;

@Component
public class TokenUtility {
    private static final String TOKEN_SECRET = "Akshay";

    public String createToken(Integer id) {
        //to set algorithm
        try {
            Algorithm algo = Algorithm.HMAC256(TOKEN_SECRET);
            String token = JWT.create().withClaim("id_key", id).sign(algo);
            return token;
        } catch (JWTCreationException exception) {
            exception.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Long decodeToken(String token) throws SignatureVerificationException {
        //for verification algorithm
        Verification verification = null;
        try {
            verification = JWT.require(Algorithm.HMAC256(TOKEN_SECRET));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        JWTVerifier jwtVerifier = verification.build();
        //to decode token
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        Claim idClaim = decodedJWT.getClaim("id_key");
        Long id = Long.valueOf(idClaim.asInt());
        return id;
    }
}
