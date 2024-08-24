package br.com.microservico.product_api.modules.jwt.service;

import br.com.microservico.product_api.config.exception.AuthenticationException;
import br.com.microservico.product_api.config.exception.ValidationException;
import br.com.microservico.product_api.modules.jwt.dto.JwtUserResponse;
import io.jsonwebtoken.Jwts;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static org.springframework.util.ObjectUtils.isEmpty;


@Service
public class JwtService {

    private static final String BEARER = "bearer";
    @Value("${app-config.secrets.api-secret}")
    private String apiSecret;

    public void validateAuthorization(String token) {
        try {
         var accessToken = extractToken(token);
         var claims = Jwts
                 .parserBuilder()
                 .setSigningKey(apiSecret)
                 .build()
                 .parseClaimsJws(accessToken)
                 .getBody();

         var user = JwtUserResponse.getUser(claims);

         if (isEmpty(user) || isEmpty(user.getId())) {
             throw new AuthenticationException("User not authenticated");
         }

        } catch (Exception e) {
            e.printStackTrace();
            throw new AuthenticationException("Invalid token");
        }
    }

    private String extractToken(String token) {
        if (isEmpty(token)) {
            throw new AuthenticationException("Token must be informed");
        }
        if (token.toLowerCase().contains(BEARER)) {
            token = token.toLowerCase();
            token = token.replace(BEARER, Strings.EMPTY);
        }
        return token;
    }
}
