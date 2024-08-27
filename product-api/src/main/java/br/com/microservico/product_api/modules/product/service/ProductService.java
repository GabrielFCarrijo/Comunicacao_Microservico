package br.com.microservico.product_api.modules.product.service;

import br.com.microservico.product_api.config.exception.SuccesResoponse;
import br.com.microservico.product_api.config.exception.ValidationException;
import br.com.microservico.product_api.modules.category.service.CategoryService;
import br.com.microservico.product_api.modules.product.dto.ProductRequest;
import br.com.microservico.product_api.modules.product.dto.ProductResponse;
import br.com.microservico.product_api.modules.product.dto.ProductStockDTO;
import br.com.microservico.product_api.modules.product.model.Product;
import br.com.microservico.product_api.modules.product.repository.ProductRepository;
import br.com.microservico.product_api.modules.sales.dto.SalesConfirmationDTO;
import br.com.microservico.product_api.modules.sales.enums.SalesStatusEnum;
import br.com.microservico.product_api.modules.sales.rabbit.SalesConfirmationSender;
import br.com.microservico.product_api.modules.supplier.service.SupplierService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.util.ObjectUtils.isEmpty;

@Slf4j
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Lazy
    private CategoryService categoryService;
    @Lazy
    private SupplierService supplierService;
    @Autowired
    private SalesConfirmationSender salesConfirmationSender;

    public ProductResponse save(ProductRequest request) {
        validadeProductObject(request);
        var supplier = supplierService.verifySupplierExists(request.getSupplier());
        var category = categoryService.verifyCategoryExists(request.getCategory());
        var product = productRepository.save(Product.of(request, supplier, category));
        return ProductResponse.of(productRepository.save(product));
    }

    public ProductResponse update(ProductRequest request, Integer id) {
        validadeProductObject(request);
        validateInformedId(id);

        var supplier = supplierService.verifySupplierExists(request.getSupplier());
        var category = categoryService.verifyCategoryExists(request.getCategory());
        var product = Product.of(request, supplier, category);
        product.setId(id);

        productRepository.save(product);

        return ProductResponse.of(product);
    }

    public List<ProductResponse> findAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(ProductResponse::of)
                .toList();
    }

    public List<ProductResponse> findByName(String name) {
        if (isEmpty(name)) {
            throw new ValidationException("Product Name must be informed");
        }
        return productRepository.findByName(name)
                .stream()
                .map(ProductResponse::of)
                .collect(Collectors.toList());
    }

    public ProductResponse findById(Integer id) {
        validateInformedId(id);

        return productRepository.findById(Math.toIntExact(id))
                .map(ProductResponse::of)
                .orElseThrow(() -> new ValidationException("Product ID not found"));
    }

    public List<ProductResponse> findBySupplierId(Integer supplierId) {
        validateInformedId(supplierId);

        return productRepository
                .findBySupplierId(supplierId)
                .stream()
                .map(ProductResponse::of)
                .collect(Collectors.toList());
    }

    public List<ProductResponse> findByCategoryId(Integer categoryId) {
        validateInformedId(categoryId);
        return productRepository
                .findByCategoryId(categoryId)
                .stream()
                .map(ProductResponse::of)
                .collect(Collectors.toList());
    }

    public Boolean existsByCategoryId(Integer id) {
        return productRepository.existsByCategoryId(id);
    }

    public Boolean existsBySupplierId(Integer id) {
        return productRepository.existsBySupplierId(id);
    }

    public SuccesResoponse delete(Integer id) {
        validateInformedId(id);
        productRepository.deleteById(id);
        return SuccesResoponse.create("The product has been deleted");
    }

    public void validateInformedId(Integer id) {
        if (isEmpty(id)) {
            throw new ValidationException("Product ID must be informed");
        }
    }


    public void updateProductStock(ProductStockDTO product) {
        try {
            validateStockUpdateData(product);
            updateStock(product);
        } catch (Exception e) {
            log.error("An error occurred while updating product stock: {}", e.getMessage());
            salesConfirmationSender.sendSalesConfirmationMessage(new SalesConfirmationDTO(product.getSalesId(), SalesStatusEnum.REJECTED));
        }
    }

    private void updateStock(ProductStockDTO product) {
        product.getProducts().forEach(salesProduct -> {
            Product existingProduct = productRepository.findProductId(salesProduct.getProductId());
            if (salesProduct.getQuantity() > existingProduct.getQuantityAvailable()) {
                throw new ValidationException(
                        String.format("The product %s is out of stock", existingProduct.getId())
                );
            }
            existingProduct.updateStock(salesProduct.getQuantity());
            productRepository.save(existingProduct);
            var aprovedMessage = new SalesConfirmationDTO(product.getSalesId(), SalesStatusEnum.APROVED);
            salesConfirmationSender.sendSalesConfirmationMessage(aprovedMessage);
        });
    }

    private void validateStockUpdateData(ProductStockDTO product) {
        if (isEmpty(product) || isEmpty( product.getSalesId())) {
            throw new ValidationException("The product data or sales id must be provided");
        }
        if (isEmpty(product.getProducts())) {
            throw new ValidationException("Product must be informed");
        }
        product.getProducts().forEach(salesProduct -> {
            if (isEmpty(salesProduct.getQuantity())
                || isEmpty(salesProduct.getProductId())) {
                throw new ValidationException("The productID and the quantity must be informed.");
            }
        });
    }


    private void validadeProductObject(ProductRequest request) {
        if (isEmpty(request.getName())) {
            throw new ValidationException("Product Name must be informed");
        }
        if (isEmpty(request.getQuantity())) {
            throw new ValidationException("Product Quantity must be informed");
        }
        if (isEmpty(request.getCategory()) || request.getCategory() == null) {
            throw new ValidationException("Product Category ID must be informed");
        }
        if (isEmpty(request.getSupplier()) || request.getSupplier() == null) {
            throw new ValidationException("Product Supplier ID must be informed");
        }
    }
}
