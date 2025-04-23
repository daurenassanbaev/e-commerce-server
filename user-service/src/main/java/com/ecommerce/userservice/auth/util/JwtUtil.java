package com.ecommerce.userservice.auth.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class JwtUtil {

    public static UUID extractSubject(String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        DecodedJWT jwt = JWT.decode(token);
        return UUID.fromString(jwt.getSubject());
    }

}
