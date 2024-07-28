package br.com.microservico.product_api.modules.product.dto;

import br.com.microservico.product_api.modules.category.dto.CategoryResponse;
import br.com.microservico.product_api.modules.supplier.dto.SupplierResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProductRequest {
    private String name;
    @JsonProperty("quantity")
    private Integer quantity;
    private Integer supplier;
    private Integer category;
}