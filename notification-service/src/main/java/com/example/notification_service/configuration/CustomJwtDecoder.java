package com.example.notification_service.configuration;

import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

@Slf4j
@Component
public class CustomJwtDecoder implements JwtDecoder {

    @Value("${jwt.signerKey}")
    private String signerKey;

    @Override
    public Jwt decode(String token) throws JwtException {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);

            MACVerifier verifier = new MACVerifier(signerKey.getBytes());
            boolean verified = signedJWT.verify(verifier);
            if (!verified) {
                throw new JwtException("Invalid JWT signature");
            }

            Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();
            if (expiryTime == null || expiryTime.before(new Date())) {
                throw new JwtException("JWT token is expired or has no expiry");
            }

            Date issueTime = signedJWT.getJWTClaimsSet().getIssueTime();
            Instant issuedAt = (issueTime != null) ? issueTime.toInstant() : Instant.now();

            return new Jwt(
                    token,
                    issuedAt,
                    expiryTime.toInstant(),
                    signedJWT.getHeader().toJSONObject(),
                    signedJWT.getJWTClaimsSet().getClaims()
            );
        } catch (JwtException e) {
            throw e;
        } catch (Exception e) {
            log.error("JWT decode error: {}", e.getMessage());
            throw new JwtException("Cannot decode JWT token: " + e.getMessage());
        }
    }
}
