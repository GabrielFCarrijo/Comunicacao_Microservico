package br.com.microservico.product_api.modules.jwt.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtUserResponse {

    private Integer id;
    private String name;
    private String email;

    public static JwtUserResponse getUser(Claims jwtclains) {
        try {
            return new ObjectMapper().convertValue(jwtclains.get("authUser"), JwtUserResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
