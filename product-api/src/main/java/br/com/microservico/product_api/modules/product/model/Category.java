package br.com.microservico.product_api.modules.product.model;

import br.com.microservico.product_api.modules.product.dto.CategoryRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@Entity
@Table(name = "CATEGORY")
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

    public static Category of(CategoryRequest request) {
        var category = new Category();
        BeanUtils.copyProperties(category, request);
        return category;
    }
}
