package br.com.microservico.product_api.modules.supplier.service;

import br.com.microservico.product_api.config.exception.SuccesResoponse;
import br.com.microservico.product_api.config.exception.ValidationException;
import br.com.microservico.product_api.modules.product.service.ProductService;
import br.com.microservico.product_api.modules.supplier.dto.SupplierRequest;
import br.com.microservico.product_api.modules.supplier.dto.SupplierResponse;
import br.com.microservico.product_api.modules.supplier.model.Supplier;
import br.com.microservico.product_api.modules.supplier.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;
    @Lazy
    private ProductService productService;

    public SupplierResponse save(SupplierRequest request) {
        validateSupplierNameInformed(request);
        var supplier = supplierRepository.save(Supplier.of(request));
        return SupplierResponse.of(supplier);
    }

    public SupplierResponse update(SupplierRequest request, Integer id) {
        validateSupplierNameInformed(request);

        var supplier = Supplier.of(request);
        supplier.setId(id);
        supplierRepository.save(supplier);

        return SupplierResponse.of(supplier);
    }


    private void validateSupplierNameInformed(SupplierRequest request) {
        if (isEmpty(request.getName())) {
            throw new ValidationException("Supplier's name must be informed");
        }
    }

    public Supplier verifySupplierExists(Integer supplierID) {
        validateInformedId(supplierID);
        return supplierRepository.findById(supplierID)
                .orElseThrow(() -> new ValidationException("Supplier ID not found"));
    }

    public List<SupplierResponse> findAllSupplier() {
        return supplierRepository.findAll()
                .stream()
                .map(SupplierResponse::of)
                .toList();
    }

    public List<SupplierResponse> findByName(String name) {
        return supplierRepository.findByNameContaining(name)
                .stream()
                .map(SupplierResponse::of)
                .collect(Collectors.toList());
    }

    public SupplierResponse findById(Integer id) {
        validateInformedId(id);
        return supplierRepository.findById(Math.toIntExact(id))
                .map(SupplierResponse::of)
                .orElseThrow(() -> new ValidationException("Supplier ID not found"));
    }

    public SuccesResoponse delete(Integer id) {
        validateInformedId(id);
        if (productService.existsBySupplierId(id)) {
            throw new ValidationException("Supplier cannot be deleted because it has associated products");
        }
        supplierRepository.deleteById(id);
        return SuccesResoponse.create("The supplier has been deleted");
    }

    public void validateInformedId(Integer id) {
        if (isEmpty(id)) {
            throw new ValidationException("Supplier ID must be informed");
        }
    }
}
