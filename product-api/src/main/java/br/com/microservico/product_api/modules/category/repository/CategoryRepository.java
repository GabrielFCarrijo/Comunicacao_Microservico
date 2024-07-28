package br.com.microservico.product_api.modules.category.repository;

import br.com.microservico.product_api.modules.category.model.Category;
import br.com.microservico.product_api.modules.supplier.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    List<Category> findByDescription(String description);
}
