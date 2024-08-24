package br.com.microservico.product_api.modules.category.service;

import br.com.microservico.product_api.config.exception.SuccesResoponse;
import br.com.microservico.product_api.config.exception.ValidationException;
import br.com.microservico.product_api.modules.category.dto.CategoryRequest;
import br.com.microservico.product_api.modules.category.dto.CategoryResponse;
import br.com.microservico.product_api.modules.category.model.Category;
import br.com.microservico.product_api.modules.category.repository.CategoryRepository;
import br.com.microservico.product_api.modules.product.service.ProductService;
import br.com.microservico.product_api.modules.supplier.dto.SupplierRequest;
import br.com.microservico.product_api.modules.supplier.dto.SupplierResponse;
import br.com.microservico.product_api.modules.supplier.model.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Lazy
    private ProductService productService;

    public CategoryResponse save(CategoryRequest request) {
        validateCategoryNameInformed(request);
        var category = categoryRepository.saveAndFlush(Category.of(request));
        return CategoryResponse.of(category);
    }

    public CategoryResponse update(CategoryRequest request, Integer id) {
        validateCategoryNameInformed(request);
        validateInformedId(id);

        var category = Category.of(request);
        category.setId(id);
        categoryRepository.save(category);

        return CategoryResponse.of(category);
    }

    private void validateCategoryNameInformed(CategoryRequest request) {
        if (isEmpty(request.getDescription())) {
            throw new ValidationException("Category description must be informed");
        }
    }

    public Category verifyCategoryExists(Integer categoryID) {
        validateInformedId(categoryID);
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

    public CategoryResponse findById(Integer id) {
        validateInformedId(id);
        return categoryRepository.findById(Math.toIntExact(id))
               .map(CategoryResponse::of)
               .orElseThrow(() -> new ValidationException("Category ID not found"));
    }

    public SuccesResoponse delete(Integer id) {
        validateInformedId(id);
        if (productService.existsByCategoryId(id)) {
            throw new ValidationException("Category cannot be deleted because it has associated products");
        }
        categoryRepository.deleteById(id);
        return SuccesResoponse.create("The category has been deleted");
    }

    public void validateInformedId(Integer id) {
        if (isEmpty(id)) {
            throw new ValidationException("Category ID must be informed");
        }
    }
}
