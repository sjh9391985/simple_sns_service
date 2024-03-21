package com.example.sns.configuration.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // get header
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null || header.startsWith("Bearer ")) {
            log.error("ERROR ACCURS WHILE GETTING HEADER");
            filterChain.doFilter(request, response);
            return;
        }

        try {
            final String token = header.split(" ")[1].trim();
            String username = "";

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    null, null, null
            );

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        } catch (RuntimeException e) {
            filterChain.doFilter(request, response);
            return;
        }

        filterChain.doFilter(request, response);

    }

}
