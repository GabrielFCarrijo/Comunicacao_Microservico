package br.com.microservico.product_api.modules.jwt.dto;

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
            return JwtUserResponse.builder()
                    .id((Integer) jwtclains.get("id"))
                    .name((String) jwtclains.get("name"))
                    .email((String) jwtclains.get("email"))
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
