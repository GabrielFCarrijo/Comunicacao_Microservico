package br.com.microservico.product_api.modules.product.dto;

import br.com.microservico.product_api.modules.product.model.Category;
import com.fasterxml.jackson.databind.util.BeanUtil;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class CategoryResponse {

    private Integer id;
    private String description;

    public static CategoryResponse of(Category category) {
        var response = new CategoryResponse();
        BeanUtils.copyProperties(category, response);
        return response;
    }

}
