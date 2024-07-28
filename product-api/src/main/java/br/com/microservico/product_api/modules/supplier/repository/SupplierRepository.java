package br.com.microservico.product_api.modules.supplier.repository;

import br.com.microservico.product_api.modules.supplier.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Integer> {
}
