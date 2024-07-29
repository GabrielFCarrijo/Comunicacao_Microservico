package br.com.microservico.product_api.modules.product.repository;

import br.com.microservico.product_api.modules.product.model.Product;
import br.com.microservico.product_api.modules.supplier.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByName(String name);
    List<Product> findBycategoryID(Integer id);
    List<Product> findBySupplierID(Integer id);
}
