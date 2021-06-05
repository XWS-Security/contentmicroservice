package org.nistagram.contentmicroservice.security.auth;

import org.nistagram.contentmicroservice.security.TokenUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private TokenUtils tokenUtils;
    private UserDetailsService userDetailsService;

    public TokenAuthenticationFilter(TokenUtils tokenHelper, UserDetailsService userDetailsService) {
        this.tokenUtils = tokenHelper;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        System.out.println("DO FILTER INTERNAL");
        String username;
        String authToken = tokenUtils.getToken(request);
        System.out.println("TOKEN: " + authToken);

        if (authToken != null) {
            username = tokenUtils.getUsernameFromToken(authToken);
            System.out.println("Username from token: " + username);

            if (username != null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                if (tokenUtils.validateToken(authToken, userDetails)) {
                    TokenBasedAuthentication authentication = new TokenBasedAuthentication(userDetails);
                    authentication.setToken(authToken);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }

        chain.doFilter(request, response);
    }

}
