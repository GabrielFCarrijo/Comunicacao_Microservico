package br.com.microservico.product_api.modules.supplier.service;

import br.com.microservico.product_api.config.exception.ValidationException;
import br.com.microservico.product_api.modules.supplier.dto.SupplierRequest;
import br.com.microservico.product_api.modules.supplier.dto.SupplierResponse;
import br.com.microservico.product_api.modules.supplier.model.Supplier;
import br.com.microservico.product_api.modules.supplier.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.springframework.util.ObjectUtils.isEmpty;


@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    public SupplierResponse save(SupplierRequest request) {
        validadeSupplierNameInformed(request);
        var supplier = supplierRepository.save(Supplier.of(request));
        return  SupplierResponse.of(supplier);
    }

    private void validadeSupplierNameInformed(SupplierRequest request) {
        if (isEmpty(request.getName())) {
            throw new ValidationException("Supplier's name must be informed");
        }
    }

    public Supplier verificationSupplierExists(Integer supplierID) {
        return supplierRepository.findById(supplierID).orElseThrow(() -> new ValidationException("Supplier ID not found"));
    }
}