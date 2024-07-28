package br.com.microservico.product_api.modules.category.repository;

import br.com.microservico.product_api.modules.category.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}