package com.jwt.security;

import com.jwt.dto.AuthenticationResponse;
import com.jwt.service.TokenService;
import com.jwt.util.StaticVariables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class SecurityFilter extends GenericFilter {

    @Autowired
    private TokenService tokenService;

    private static final String AUTHORIZATION="Authorization";

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        if(!unauthorizedControl(request.getRequestURI())){
            try{
                if(!tokenService.checkToToken(new AuthenticationResponse(request.getHeader(AUTHORIZATION)))){
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Invalid JWT Token");
                    return;
                }
            }catch (Exception e){
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Invalid JWT Token Error");
                return;
            }
        }

        filterChain.doFilter(req, res);
    }

    private Boolean unauthorizedControl(String url){
        for (String s : StaticVariables.All_PERMISSIONS) {
            if (url.equals(s)) return true;
        }
        return false;
    }

}
