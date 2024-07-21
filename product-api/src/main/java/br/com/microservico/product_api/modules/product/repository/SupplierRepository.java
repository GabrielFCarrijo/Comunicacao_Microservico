package br.com.microservico.product_api.modules.product.repository;

import br.com.microservico.product_api.modules.product.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Integer> {
}
