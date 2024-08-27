package br.com.microservico.product_api.modules.sales.dto;

import br.com.microservico.product_api.modules.sales.enums.SalesStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesConfirmationDTO {

    private String salesId;
    private SalesStatusEnum status;
}
