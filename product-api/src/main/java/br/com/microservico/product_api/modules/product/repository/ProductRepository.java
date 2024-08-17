package br.com.microservico.product_api.modules.product.repository;

import br.com.microservico.product_api.modules.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByName(String name);
    List<Product> findBySupplierId(Integer supplierId);
    List<Product> findByCategoryId(Integer categoryId);
    Boolean existsByCategoryId(Integer categoryId);
    Boolean existsBySupplierId(Integer supplierId);

}
