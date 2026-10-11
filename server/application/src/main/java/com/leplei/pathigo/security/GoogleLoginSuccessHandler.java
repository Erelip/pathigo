package com.leplei.pathigo.security;

import com.leplei.pathigo.models.AuthTokens;
import com.leplei.pathigo.services.AuthenticationService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;

@Component
public class GoogleLoginSuccessHandler
        implements AuthenticationSuccessHandler {

    private final AuthenticationService authenticationService;

    @Value("${app.frontend.url}")
    private String frontendUrl;

    @Value("${app.auth.cookie-secure:true}")
    private boolean cookieSecure;

    @Value("${app.auth.cookie-same-site:None}")
    private String cookieSameSite;

    public GoogleLoginSuccessHandler(
            AuthenticationService authenticationService
    ) {
        this.authenticationService = authenticationService;
    }

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {

        OidcUser googleUser = (OidcUser) authentication.getPrincipal();

        assert googleUser != null;
        String googleSubject = googleUser.getSubject();
        String email = googleUser.getEmail();
        String fullName = googleUser.getFullName();
        Boolean emailVerified = googleUser.getEmailVerified();

        if (googleSubject == null
                || email == null
                || !Boolean.TRUE.equals(emailVerified)) {
            response.sendRedirect(frontendUrl + "/auth/login?error=google");
            return;
        }

        AuthTokens tokens = authenticationService.loginOrRegisterGoogle(
                googleSubject,
                email,
                fullName
        );

        ResponseCookie accessCookie = ResponseCookie
                .from("access_token", tokens.accessToken())
                .httpOnly(true)
                .secure(cookieSecure)
                .path("/")
                .maxAge(Duration.ofMinutes(15))
                .sameSite(cookieSameSite)
                .build();

        ResponseCookie refreshCookie = ResponseCookie
                .from("refresh_token", tokens.refreshToken())
                .httpOnly(true)
                .secure(cookieSecure)
                .path("/")
                .maxAge(Duration.ofDays(7))
                .sameSite(cookieSameSite)
                .build();

        response.addHeader(
                HttpHeaders.SET_COOKIE, accessCookie.toString()
        );
        response.addHeader(
                HttpHeaders.SET_COOKIE, refreshCookie.toString()
        );

        response.sendRedirect(frontendUrl + "/dashboard");
    }
}
