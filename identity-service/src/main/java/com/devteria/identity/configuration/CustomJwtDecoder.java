package com.devteria.identity.configuration;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;

import com.nimbusds.jwt.SignedJWT;

@Component
public class CustomJwtDecoder implements JwtDecoder {

    @Autowired
    private com.devteria.identity.service.AuthenticationService authenticationService;

    @Override
    public Jwt decode(String token) throws JwtException {

        try {
            // Verify token using AuthenticationService (which checks signature and expiry)
            authenticationService.verifyToken(token, false);
            SignedJWT signedJWT = SignedJWT.parse(token);

            java.util.Date issueTime = signedJWT.getJWTClaimsSet().getIssueTime();
            java.util.Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

            if (expiryTime == null) {
                throw new JwtException("Token has no expiration time");
            }

            java.time.Instant issuedAt = (issueTime != null) ? issueTime.toInstant() : java.time.Instant.now();

            return new Jwt(
                    token,
                    issuedAt,
                    expiryTime.toInstant(),
                    signedJWT.getHeader().toJSONObject(),
                    signedJWT.getJWTClaimsSet().getClaims());
        } catch (com.devteria.identity.exception.AppException | ParseException | com.nimbusds.jose.JOSEException e) {
            throw new JwtException("Invalid token", e);
        }
    }
}
