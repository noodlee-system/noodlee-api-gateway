package com.noodleesystem.gateway.authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtFilter implements javax.servlet.Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException { }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        final String path = this.getRequestUrl(httpServletRequest);
        if (path.matches("(.*)/v2/api-docs")) {
            filterChain.doFilter(servletRequest, servletResponse);
        }

        final String header = httpServletRequest.getHeader("authorization");
        final String tokenBeginningPhrase = "Bearer ";
        final String signatureKey = "c9e0fffa-6333-47d9-b621-ba44dc9523a7";

        if (header == null || !header.startsWith(tokenBeginningPhrase)) {
            ((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED, "The token is not valid.");
        } else {
            try {
                final String token = header.substring(tokenBeginningPhrase.length());

                Claims claims = Jwts
                        .parser()
                        .setSigningKey(signatureKey)
                        .parseClaimsJws(token)
                        .getBody();

                servletRequest.setAttribute("claims", claims);
            } catch (final SignatureException e) {
                ((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED, "The token is not valid.");
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() { }

    private String getRequestUrl(HttpServletRequest request) {
        StringBuilder requestURL = new StringBuilder(request.getRequestURL().toString());
        String queryString = request.getQueryString();

        if (queryString == null) {
            return requestURL.toString();
        } else {
            return requestURL.append('?').append(queryString).toString();
        }
    }
}
