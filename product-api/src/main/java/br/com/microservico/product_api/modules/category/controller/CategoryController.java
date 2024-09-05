package br.com.microservico.product_api.modules.category.controller;

import br.com.microservico.product_api.config.exception.SuccesResoponse;
import br.com.microservico.product_api.modules.category.dto.CategoryRequest;
import br.com.microservico.product_api.modules.category.dto.CategoryResponse;
import br.com.microservico.product_api.modules.category.service.CategoryService;
import br.com.microservico.product_api.modules.product.dto.ProductRequest;
import br.com.microservico.product_api.modules.product.dto.ProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public CategoryResponse save(@RequestBody CategoryRequest request) {
        return categoryService.save(request);
    }
    @GetMapping
    public List<CategoryResponse> findAll() {
        return categoryService.findAllCategory();
    }
    @GetMapping("/{id}")
    public CategoryResponse findById(@PathVariable Integer id) {
        return categoryService.findByIdResponse(id);
    }

    @GetMapping("/description")
    public List<CategoryResponse> findByDescription(@PathVariable String description) {
        return categoryService.findByDescription(description);
    }

    @PutMapping("{id}")
    public CategoryResponse update(@RequestBody CategoryRequest request, @PathVariable Integer id) {
        return categoryService.update(request, id);
    }

    @DeleteMapping("{id}")
    public SuccesResoponse deleteById(@PathVariable Integer id) {
        return categoryService.delete(id);
    }

}
