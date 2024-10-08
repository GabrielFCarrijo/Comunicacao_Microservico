package br.com.microservico.product_api.modules.product.controller;

import br.com.microservico.product_api.config.exception.SuccesResoponse;
import br.com.microservico.product_api.modules.product.dto.ProductCheckStockRequest;
import br.com.microservico.product_api.modules.product.dto.ProductRequest;
import br.com.microservico.product_api.modules.product.dto.ProductResponse;
import br.com.microservico.product_api.modules.product.dto.ProductSalesResponse;
import br.com.microservico.product_api.modules.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ProductResponse save(@RequestBody ProductRequest request) {
        return productService.save(request);
    }

    @GetMapping
    public List<ProductResponse> findAll() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public ProductResponse findById(@PathVariable Integer id) {
        return productService.findByIdResponse(id);
    }

    @GetMapping("name/{name}")
    public List<ProductResponse> findByName(@PathVariable String name) {
        return productService.findByName(name);
    }

    @GetMapping("category/{idcategory}")
    public List<ProductResponse> findByCategory(@PathVariable Integer idcategory) {
        return productService.findByCategoryId(idcategory);
    }

    @GetMapping("supplier/{idsupplier}")
    public List<ProductResponse> findBySupplier(@PathVariable Integer idsupplier) {
        return productService.findBySupplierId(idsupplier);
    }

    @PutMapping("{id}")
    public ProductResponse update(@RequestBody ProductRequest request, @PathVariable Integer id) {
        return productService.update(request, id);
    }

    @DeleteMapping("{id}")
    public SuccesResoponse deleteById(@PathVariable Integer id) {
        return productService.delete(id);
    }

    @PostMapping("check-stock")
    public SuccesResoponse checkProductsStock(@RequestBody ProductCheckStockRequest request) {
        return productService.checkProductsStock(request);
    }

    @GetMapping("{id}/sales")
    public ProductSalesResponse findProductSales(@PathVariable Integer productId) {
        return productService.findProductSales(productId);
    }
}
