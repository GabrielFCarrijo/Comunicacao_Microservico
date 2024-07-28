package br.com.microservico.product_api.modules.category.service;

import br.com.microservico.product_api.config.exception.ValidationException;
import br.com.microservico.product_api.modules.category.dto.CategoryRequest;
import br.com.microservico.product_api.modules.category.dto.CategoryResponse;
import br.com.microservico.product_api.modules.category.model.Category;
import br.com.microservico.product_api.modules.category.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.springframework.util.ObjectUtils.isEmpty;


@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryResponse save(CategoryRequest request) {
        validadeCategoryNameInformed(request);
        var category = categoryRepository.save(Category.of(request));
        return  CategoryResponse.of(category);
    }

    private void validadeCategoryNameInformed(CategoryRequest request) {
        if (isEmpty(request.getDescription())) {
            throw new ValidationException("Category description must be informed");
        }
    }

    public Category verificationCategoryExists(Integer categoryID) {
        return categoryRepository.findById(categoryID)
                .orElseThrow(() -> new ValidationException("Category ID not found"));
    }

}
