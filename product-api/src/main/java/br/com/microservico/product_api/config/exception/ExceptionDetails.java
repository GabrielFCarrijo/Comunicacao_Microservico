package br.com.microservico.product_api.config.exception;

import lombok.Data;

@Data
public class ExceptionDetails {

    private String message;
    private String status;
}
