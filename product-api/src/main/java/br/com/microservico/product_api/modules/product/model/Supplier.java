package br.com.microservico.product_api.modules.product.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "SUPPLIER")
@AllArgsConstructor
@NoArgsConstructor
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name="NAME", nullable = false)
    private String name;

}
