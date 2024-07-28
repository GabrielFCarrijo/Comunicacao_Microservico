package br.com.microservico.product_api.modules.category.service;

import br.com.microservico.product_api.config.exception.ValidationException;
import br.com.microservico.product_api.modules.category.dto.CategoryRequest;
import br.com.microservico.product_api.modules.category.dto.CategoryResponse;
import br.com.microservico.product_api.modules.category.model.Category;
import br.com.microservico.product_api.modules.category.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryResponse save(CategoryRequest request) {
        validateCategoryNameInformed(request);
        var category = categoryRepository.saveAndFlush(Category.of(request));
        return CategoryResponse.of(category);
    }

    private void validateCategoryNameInformed(CategoryRequest request) {
        if (isEmpty(request.getDescription())) {
            throw new ValidationException("Category description must be informed");
        }
    }

    public Category verifyCategoryExists(Integer categoryID) {
        return categoryRepository.findById(categoryID)
                .orElseThrow(() -> new ValidationException("Category ID not found"));
    }

    public List<CategoryResponse> findAllCategory() {
        return categoryRepository.findAll()
                .stream()
                .map(CategoryResponse::of)
                .toList();
    }

    public List<CategoryResponse> findByDescription(String description) {
        return categoryRepository.findByDescription(description)
                .stream().map(CategoryResponse::of)
                .collect(Collectors.toList());
    }

    public CategoryResponse findById(Long id) {
        return categoryRepository.findById(Math.toIntExact(id))
               .map(CategoryResponse::of)
               .orElseThrow(() -> new ValidationException("Category ID not found"));
    }
}
