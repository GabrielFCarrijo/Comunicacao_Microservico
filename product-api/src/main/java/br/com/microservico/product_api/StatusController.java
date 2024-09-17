package br.com.microservico.product_api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Objects;

@RestController
@RequestMapping("/")
public class StatusController {

    @GetMapping
    public ResponseEntity<HashMap<String, Object>> getRoot() {
        var response = getSuccesResponse();
        return ResponseEntity.ok(response);
    }

    @GetMapping("api/status")
    public ResponseEntity<HashMap<String, Object>> getApiStatus() {
        var response = getSuccesResponse();
        return ResponseEntity.ok(response);
    }

    private HashMap<String, Object> getSuccesResponse() {
        var response = new HashMap<java.lang.String, java.lang.Object>();

        response.put("service", "`Product-Api");
        response.put("status", "UP");
        response.put("httpsStatus", HttpStatus.OK.value());
        return response;
    }
}
