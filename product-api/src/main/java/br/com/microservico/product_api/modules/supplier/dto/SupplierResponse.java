package br.com.microservico.product_api.modules.supplier.dto;

import br.com.microservico.product_api.modules.supplier.model.Supplier;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class SupplierResponse {

    private Integer id;
    private String name;

    public static SupplierResponse of(Supplier supplier) {
        var response = new SupplierResponse();
        response.setId(supplier.getId());
        response.setName(supplier.getName());
        return response;
    }

}