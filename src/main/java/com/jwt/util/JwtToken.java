package com.jwt.util;

import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

@Component
public class JwtToken {

    private static String SECRET_KEY = "JIUzI1NiIsInR5cCI6IkpXVCJ9hhbg=asdalejn0JANTIzODg0YjI=";
    private static String HEADER = "{\"alg\":\"HS256\",\"typ\":\"JWT\"}";

    public String getUsername(String token) throws Exception {
        try {
            String[] array = token.split("\\.");
            return base64Decode(array[1]);
        }catch (Exception e) {
            throw new Exception("Token Error");
        }
    }

    public String createToken(String username) {
        return  base64Encode(HEADER) + "." + base64Encode(username) + "." + HMACSHA256(base64Encode(HEADER) + "." + base64Encode(username), SECRET_KEY);
    }

    private String base64Decode(String data) {
        byte[] decodedBytes = Base64.getDecoder().decode(data);
        String decodedString = new String(decodedBytes);
        return decodedString;
    }

    private String base64Encode(String data) {
        return Base64.getEncoder().encodeToString(data.getBytes());
    }

    private String HMACSHA256(String data, String secret) {
        try {
            byte[] hash = secret.getBytes(StandardCharsets.UTF_8);
            Mac sha256Hmac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(hash, "HmacSHA256");
            sha256Hmac.init(secretKey);
            byte[] signedBytes = sha256Hmac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return base64Encode(signedBytes.toString());
        } catch (NoSuchAlgorithmException | InvalidKeyException ex) {
            return null;
        }
    }
}
