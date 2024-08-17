package br.com.microservico.product_api.config.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SuccesResoponse {

    private Integer status;
    private String message;

    public static SuccesResoponse create(String message) {
        return SuccesResoponse
                .builder()
                .status(200)
                .message(message)
                .build();
    }
}
