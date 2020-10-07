package com.jwt.service;

import com.jwt.dto.AuthenticationRequest;
import com.jwt.dto.AuthenticationResponse;
import com.jwt.model.Token;
import com.jwt.model.User;
import com.jwt.repository.TokenRepository;
import com.jwt.repository.UserRepository;
import com.jwt.util.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Date;
import java.util.List;

@Service
public class TokenService {

    private final Integer validityInMilliseconds = 1800000; // 30h
    private final Integer extratime = 1800000; // 30m

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtToken jwtToken;

    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) throws Exception {
        try{
            User user = userRepository.findById(authenticationRequest.getUsername()).get();
            if(user.getPassword().equals(DigestUtils.sha256Hex(authenticationRequest.getPassword()))){
                Token token;
                try{
                    token = tokenRepository.findById(user.getUsername()).get();
                }catch (Exception e){
                    token = new Token(user.getUsername(), jwtToken.createToken(user.getUsername()), new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis() + validityInMilliseconds));
                    tokenRepository.save(token);
                }
                return new AuthenticationResponse(token.getToken());
            }
            throw new Exception("Password Error");
        }catch (Exception e){
            throw new Exception("Login Error",new Exception(e.toString()));
        }
    }

    public Boolean checkToToken(AuthenticationResponse authenticationResponse) throws Exception {
        try{
            Token token = tokenRepository.findById(jwtToken.getUsername(authenticationResponse.getToken())).get();
            if(token != null){
                if(new Date(System.currentTimeMillis()).before(token.getCancellationTime())) {
                    token.setCancellationTime(new Date(System.currentTimeMillis() + extratime));
                    return true;
                }
                tokenRepository.delete(token);
            }
            return false;
        }catch (Exception e){
            return false;
        }
    }

    public void timeOut(){
        try{
            System.out.println("Scheduled...");
            List<Token> tokens = tokenRepository.findAll();
            int i = 0;
            for (Token t: tokens) {
                if(new Date(System.currentTimeMillis()).after(t.getCancellationTime())) {
                    tokenRepository.delete(t);
                    i++;
                }
            }
            System.out.println("Number of deleted data: "+i);
            System.out.println("Scheduled is Done.");
        }catch (Exception e){
            System.out.println("Scheduled Error");
        }
    }


}
