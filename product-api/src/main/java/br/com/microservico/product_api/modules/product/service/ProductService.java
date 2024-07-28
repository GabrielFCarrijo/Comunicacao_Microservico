package br.com.microservico.product_api.modules.product.service;

import br.com.microservico.product_api.config.exception.ValidationException;
import br.com.microservico.product_api.modules.category.model.Category;
import br.com.microservico.product_api.modules.category.repository.CategoryRepository;
import br.com.microservico.product_api.modules.category.service.CategoryService;
import br.com.microservico.product_api.modules.product.dto.ProductRequest;
import br.com.microservico.product_api.modules.product.dto.ProductResponse;
import br.com.microservico.product_api.modules.product.model.Product;
import br.com.microservico.product_api.modules.product.repository.ProductRepository;
import br.com.microservico.product_api.modules.supplier.model.Supplier;
import br.com.microservico.product_api.modules.supplier.repository.SupplierRepository;
import br.com.microservico.product_api.modules.supplier.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.springframework.util.ObjectUtils.isEmpty;


@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SupplierService supplierService;

    public ProductResponse save(ProductRequest request) {
        validadeProductObject(request);
        var supplier = supplierService.verifySupplierExists(request.getSupplier());
        var category = categoryService.verifyCategoryExists(request.getCategory());
        var product = productRepository.save(Product.of(request, supplier, category));
        return ProductResponse.of(productRepository.save(product));
    }



    private void validadeProductObject(ProductRequest request) {
        if (isEmpty(request.getName())) {
            throw new ValidationException("Product Name must be informed");
        }
        if (isEmpty(request.getQuantity())){
            throw new ValidationException("Product Quantity must be informed");
        }
        if(isEmpty(request.getCategory()) || request.getCategory() == null){
            throw new ValidationException("Product Category ID must be informed");
        }
        if(isEmpty(request.getSupplier()) || request.getSupplier() == null){
            throw new ValidationException("Product Supplier ID must be informed");
        }
    }
}
