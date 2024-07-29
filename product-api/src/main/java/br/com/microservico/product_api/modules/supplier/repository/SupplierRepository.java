package br.com.microservico.product_api.modules.supplier.repository;

import br.com.microservico.product_api.modules.category.model.Category;
import br.com.microservico.product_api.modules.supplier.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupplierRepository extends JpaRepository<Supplier, Integer> {

    List<Supplier> findByName(String name);

}
