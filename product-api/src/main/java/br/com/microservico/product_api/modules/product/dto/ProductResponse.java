package br.com.microservico.product_api.modules.product.dto;

import br.com.microservico.product_api.modules.category.dto.CategoryResponse;
import br.com.microservico.product_api.modules.category.model.Category;
import br.com.microservico.product_api.modules.product.model.Product;
import br.com.microservico.product_api.modules.supplier.dto.SupplierResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {

    private Integer id;
    private String name;
    private Integer quantity;
    @JsonProperty("created_at")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime createdAt;
    private SupplierResponse supplier;
    private CategoryResponse category;

    public static ProductResponse of(Product product) {

        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .quantity(product.getQuantityAvailable())
                .createdAt(product.getCreatedAt())
                .supplier(SupplierResponse.of(product.getSupplier()))
                .category(CategoryResponse.of(product.getCategory()))
                .build();
    }

}